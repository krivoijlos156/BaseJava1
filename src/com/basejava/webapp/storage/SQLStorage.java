package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.util.SQLHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public class SQLStorage implements Storage {
    public final SQLHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.getConnection("DELETE FROM resume", null);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlHelper.getConnection("UPDATE resume SET full_name=? WHERE uuid=?",
                ps -> {
                    setParamNameUuid(ps, resume, 1, 2);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlHelper.getConnection("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> setParamNameUuid(ps, resume, 2, 1));
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.getConnectionAndReturn("SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ifNull(uuid, ps);
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.getConnection("DELETE FROM resume WHERE uuid=?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        return sqlHelper.getConnectionAndReturn("SELECT * FROM resume", (ps) -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = ifNull(null, ps);
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            Collections.sort(list);
            return list;
        });
    }

    @Override
    public int size() {
        LOG.info("Get size");
        return sqlHelper.getConnectionAndReturn("SELECT count(*) FROM resume", (ps) -> {
            ResultSet rs = ifNull(null, ps);
            return rs.getInt(1);
        });
    }

    private void setParamNameUuid(PreparedStatement ps, Resume resume, int first, int second) throws SQLException {
        ps.setString(first, resume.getFullName());
        ps.setString(second, resume.getUuid());
    }

    private ResultSet ifNull(String uuid, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new NotExistStorageException(uuid);
        }
        return rs;
    }
}
