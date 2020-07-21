package com.basejava.webapp.util;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHelper {
    private final ConnectionFactory connectionFactory;

    public SQLHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void getConnection(String sql, SetParameters setParameters) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (setParameters != null) {
                setParameters.setParameters(ps);
            }
            ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 0) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

    public <T> T getConnectionAndReturn(String sql, SetGetParameters<T> setGetParameters) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return setGetParameters.setGetParameters(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface SetParameters {
        void setParameters(PreparedStatement ps) throws SQLException;
    }

    public interface SetGetParameters<T> {
        T setGetParameters(PreparedStatement ps) throws SQLException;
    }
}
