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
    private static final String SQL_SELECT_RESUME_BY_UUID = "SELECT * FROM resume r WHERE r.uuid = ?";
    public static final String SQL_SELECT_CONTACT_BY_UUID = "SELECT * FROM contact WHERE resume_uuid = ?";
    public static final String SQL_SELECT_SECTION_BY_UUID = "SELECT * FROM section WHERE resume_uuid = ?";
    public static final String SQL_DELETE_ALL_RESUME = "DELETE FROM resume";
    public static final String SQL_UPDATE_RESUME_SET_FULL_NAME_BY_UUID = "UPDATE resume SET full_name = ? WHERE uuid = ?";
    public static final String SQL_INSERT_INTO_RESUME_UUID_FULL_NAME = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
    public static final String SQL_DELETE_RESUME_BY_UUID = "DELETE FROM resume r WHERE r.uuid = ?";
    public static final String SQL_SELECT_COUNT_RESUME = "SELECT count(*) FROM resume";
    public static final String SQL_INSERT_INTO_CONTACT_TYPE_VALUE_RESUME_UUID = "INSERT INTO contact(type, value, resume_uuid) VALUES (?, ?, ?)";
    public static final String SQL_INSERT_INTO_SECTION_TYPE_CONTEXT_RESUME_UUID = "INSERT INTO section(type, context, resume_uuid) VALUES (?, ?, ?)";
    public static final String SQL_DELETE_CONTACT_BY_RESUME_UUID = "DELETE FROM contact WHERE resume_uuid = ?";
    public static final String SQL_DELETE_SECTION_BY_RESUME_UUID = "DELETE FROM section WHERE resume_uuid = ?";
    public static final String SQL_SELECT_ALL_CONTACTS = "SELECT * FROM contact";
    public static final String SQL_SELECT_ALL_SECTIONS = "SELECT * FROM section";
    public static final String SQL_SELECT_ALL_RESUME_ORDER_BY_FULL_NAME_UUID = "SELECT * FROM resume r ORDER BY r.full_name, r.uuid";
    private final SqlExecutor sqlExecutor;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlExecutor = new SqlExecutor(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlExecutor.execute(SQL_DELETE_ALL_RESUME);
    }

    @Override
    public void update(Resume r) {
        sqlExecutor.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_RESUME_SET_FULL_NAME_BY_UUID)) {
                preparedStatement.setString(1, r.getFullName());
                preparedStatement.setString(2, r.getUuid());
                if (preparedStatement.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(r);
            deleteSections(r);
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;

        });

    }

    @Override
    public void save(Resume r) {
        sqlExecutor.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_RESUME_UUID_FULL_NAME)) {
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_RESUME_BY_UUID)) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_CONTACT_BY_UUID)) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContacts(resultSet, resume);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_SECTION_BY_UUID)) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addSections(resultSet, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlExecutor.execute(SQL_DELETE_RESUME_BY_UUID, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlExecutor.transactionalExecute(connection -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_RESUME_ORDER_BY_FULL_NAME_UUID)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid").trim();
                    map.put(uuid, new Resume(uuid, resultSet.getString("full_name")));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_CONTACTS)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = map.get(resultSet.getString("resume_uuid").trim());
                    addContacts(resultSet, resume);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_SECTIONS)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = map.get(resultSet.getString("resume_uuid").trim());
                    addSections(resultSet, resume);
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlExecutor.execute(SQL_SELECT_COUNT_RESUME, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return 0;
            }
            return resultSet.getInt(1);
        });

    }

    private void insertContacts(Resume r, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_CONTACT_TYPE_VALUE_RESUME_UUID)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_SECTION_TYPE_CONTEXT_RESUME_UUID)) {
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
        sqlExecutor.execute(SQL_DELETE_CONTACT_BY_RESUME_UUID, preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
            return null;
        });
    }

    private void deleteSections(Resume r) {
        sqlExecutor.execute(SQL_DELETE_SECTION_BY_RESUME_UUID, preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
            return null;
        });
    }

    private void addContacts(ResultSet resultSet, Resume r) throws SQLException {
        String value = resultSet.getString("value");
        ContactType type = ContactType.valueOf(resultSet.getString("type"));
        r.setContact(type, value);
    }

    private void addSections(ResultSet resultSet, Resume r) throws SQLException {
        String context = resultSet.getString("context");
        SectionType type = SectionType.valueOf(resultSet.getString("type"));
        r.setSections(type, JsonParser.read(context, Section.class));
    }
}
