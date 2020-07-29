package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SQLHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class SQLStorage implements Storage {
    private final SQLHelper sqlHelper;
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
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                        setParamCheckUpdate(ps, resume.getUuid(), resume.getFullName(), resume.getUuid());
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
                        setParameters(ps, resume.getUuid());
                        ps.execute();
                    }
                    insertContact(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        setParameters(ps, resume.getUuid(), resume.getFullName());
                        ps.execute();
                    }
                    insertContact(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = checkExistence(uuid, ps);
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContactDB(rs, r);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?",
                ps -> setParamCheckUpdate(ps, uuid, uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        Map<String, Resume> map = new LinkedHashMap<>();
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid " +
                    "ORDER BY r.full_name, r.uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    map.putIfAbsent(uuid, new Resume(uuid, rs.getString("full_name")));
                    addContactDB(rs, map.get(uuid));
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        LOG.info("Get size");
        return sqlHelper.execute("SELECT count(*) FROM resume", (ps) -> {
            ResultSet rs = checkExistence(null, ps);
            return rs.getInt(1);
        });
    }

    private void addContactDB(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value == null) { return; }
        ContactType type = ContactType.valueOf(rs.getString("type"));
        r.addContact(type, value);
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                setParameters(ps, resume.getUuid(), e.getKey().name(), e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void setParameters(PreparedStatement ps, String... param) throws SQLException {
        for (int i = 0; i < param.length; i++) {
            ps.setString(i + 1, param[i]);
        }
    }

    private Object setParamCheckUpdate(PreparedStatement ps, String uuid, String... param) throws SQLException {
        this.setParameters(ps, param);
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
        return null;
    }


    private ResultSet checkExistence(String uuid, PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            throw new NotExistStorageException(uuid);
        }
        return rs;
    }
}