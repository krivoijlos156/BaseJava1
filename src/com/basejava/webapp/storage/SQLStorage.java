package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SQLHelper;

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
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?",
                ps -> setParamAndExecute(ps, resume, 1, 2, true));
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> setParamAndExecute(ps, resume, 2, 1, false));
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ifNull(uuid, ps);
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?",
                ps -> setParamAndExecute(ps, new Resume(uuid, "b"), -1, 1, true));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        return sqlHelper.execute("SELECT * FROM resume", (ps) -> {
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
        return sqlHelper.execute("SELECT count(*) FROM resume", (ps) -> {
            ResultSet rs = ifNull(null, ps);
            return rs.getInt(1);
        });
    }

    private Object setParamAndExecute(PreparedStatement ps, Resume resume,
                                      int indexName, int indexUuid,
                                      boolean checkExist) throws SQLException {
        if (indexName == -1) {
            ps.setString(indexUuid, resume.getUuid());
        } else if (indexUuid == -1) {
            ps.setString(indexName, resume.getFullName());
        } else {
            ps.setString(indexName, resume.getFullName());
            ps.setString(indexUuid, resume.getUuid());
        }
        ps.execute();
        if (checkExist) {
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        }
        return null;
    }


    private ResultSet ifNull(String uuid, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new NotExistStorageException(uuid);
        }
        return rs;
    }
}
