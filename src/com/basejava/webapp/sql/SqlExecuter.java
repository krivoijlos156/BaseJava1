package com.basejava.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecuter<T> {
    T sqlExecute(PreparedStatement ps) throws SQLException;
}