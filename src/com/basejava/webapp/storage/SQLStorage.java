package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import com.basejava.webapp.sql.SQLHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.basejava.webapp.model.SectionType.valueOf;


public class SQLStorage implements Storage {
    private final SQLHelper sqlHelper;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SQLStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
                    deletePart(resume, conn, "DELETE FROM contact WHERE resume_uuid=?");
                    deletePart(resume, conn, "DELETE FROM section WHERE resume_uuid=?");
                    insertContact(resume, conn);
                    insertSection(resume, conn);
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
                    insertSection(resume, conn);
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
                        " LEFT JOIN section s " +
                        "        ON r.uuid = s.resume_uuid " +
                        "     WHERE r.uuid =? ",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = checkExistence(uuid, ps);
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContactDB(rs, r);
                        addSectionDB(rs, r);
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
                    "ORDER BY r.full_name, r.uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    map.putIfAbsent(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    addContactDB(rs, map.get(uuid));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM resume r " +
                    " LEFT JOIN section s " +
                    "        ON r.uuid = s.resume_uuid ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    addSectionDB(rs, map.get(uuid));
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
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }

    private void addSectionDB(ResultSet rs, Resume r) throws SQLException {
        String type = rs.getString("type_s");
        if (type != null) {
            SectionType section = valueOf(type);
            switch (section) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(section, new TextSection(rs.getString("value_s")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] mas = rs.getString("value_s").split("\n");
                    r.addSection(section, new ListSection(mas));
                    break;
            }
        }
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

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type_s, value_s) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                switch (e.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        setParameters(ps, resume.getUuid(), e.getKey().name(), ((TextSection) e.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> items = ((ListSection) e.getValue()).getItems();
                        StringBuilder itemsLine = new StringBuilder();
                        for (String s : items) {
                            itemsLine.append(s).append("\n");
                        }
                        setParameters(ps, resume.getUuid(), e.getKey().name(), itemsLine.toString());
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deletePart(Resume resume, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, resume.getUuid());
            ps.execute();
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