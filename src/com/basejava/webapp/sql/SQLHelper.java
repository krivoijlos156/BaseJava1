package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {
    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, SqlExecuter<T> sqlExecute) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return sqlExecute.sqlExecute(ps);
        } catch (SQLException e) {
            if (e.getErrorCode() == 0) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

    public interface SqlExecuter<T> {
        T sqlExecute(PreparedStatement ps) throws SQLException;
    }
}
