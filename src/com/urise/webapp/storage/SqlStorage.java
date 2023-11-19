package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });

    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
            return null;
        });
        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            sqlHelper.execute("INSERT INTO contact(type, value, resume_uuid) VALUES (?, ?, ?)", preparedStatement -> {
                preparedStatement.setString(1, e.getKey().name());
                preparedStatement.setString(2, e.getValue());
                preparedStatement.setString(3, r.getUuid());
                preparedStatement.execute();
                return null;
            });
        }
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "   LEFT JOIN contact c" +
                        "      ON r.uuid = c.resume_uuid " +
                        "  WHERE r.uuid = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        String value = resultSet.getString("value");
                        ContactType type = ContactType.valueOf(resultSet.getString("type"));
                        r.setContact(type, value);
                    } while (resultSet.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resumeList.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
            }
            return null;
        });
        return resumeList;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return 0;
            }
            return resultSet.getInt(1);
        });

    }
}
