package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlExecutor {
    private final ConnectionFactory connectionFactory;

    public SqlExecutor(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public <T> T execute(String sql, SqlExecutable<T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransactionalExecutable<T> sqlTransaction) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = sqlTransaction.execute(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(null);
                }
                connection.rollback();
                throw new StorageException(e);
            }

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
