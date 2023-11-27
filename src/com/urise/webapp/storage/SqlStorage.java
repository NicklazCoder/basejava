package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlExecutor;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlExecutor sqlExecutor;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlExecutor = new SqlExecutor(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlExecutor.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlExecutor.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, r.getFullName());
                preparedStatement.setString(2, r.getUuid());
                if (preparedStatement.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(r);
            insertContacts(r, connection);
            return null;

        });

    }

    @Override
    public void save(Resume r) {
        sqlExecutor.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, r.getFullName());
                preparedStatement.execute();
            }
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlExecutor.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    getContacts(resultSet, resume);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    getSections(resultSet, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlExecutor.execute("DELETE FROM resume r WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlExecutor.execute("" +
                "SELECT * FROM resume r " +
                "   LEFT JOIN contact c" +
                "      ON r.uuid = c.resume_uuid " +
                "  ORDER BY r.full_name, r.uuid", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid").trim();
                Resume resume = map.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, resultSet.getString("full_name"));
                    map.put(uuid, resume);
                }
                getContacts(resultSet, resume);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlExecutor.execute("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return 0;
            }
            return resultSet.getInt(1);
        });

    }

    private void insertContacts(Resume r, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact(type, value, resume_uuid) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                preparedStatement.setString(1, e.getKey().name());
                preparedStatement.setString(2, e.getValue());
                preparedStatement.setString(3, r.getUuid());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void insertSections(Resume r, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section(type, context, resume_uuid) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                preparedStatement.setString(1, e.getKey().name());
                Section section = e.getValue();
                preparedStatement.setString(2, JsonParser.write(section, Section.class));
                preparedStatement.setString(3, r.getUuid());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteContacts(Resume r) {
        sqlExecutor.execute("DELETE FROM contact WHERE resume_uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
            return null;
        });
    }

    private void getContacts(ResultSet resultSet, Resume r) throws SQLException {
        String value = resultSet.getString("value");
        ContactType type = ContactType.valueOf(resultSet.getString("type"));
        r.setContact(type, value);
    }
    private void getSections(ResultSet resultSet, Resume r) throws SQLException {
        String context = resultSet.getString("context");
        SectionType type = SectionType.valueOf(resultSet.getString("type"));
        r.setSections(type, JsonParser.read(context, Section.class));
    }
}
