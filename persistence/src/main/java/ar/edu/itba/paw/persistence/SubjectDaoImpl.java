package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectDaoImpl implements SubjectDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public SubjectDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("subjects").usingGeneratedKeyColumns("subjectId");
    }

    @Override
    public Subject create(String code, String name) {
        final Map<String, Object> args = new HashMap<>();
        args.put("code", code);
        args.put("subjectName", name);
        final Long subjectId = jdbcInsert.executeAndReturnKey(args).longValue();
        return new Subject(subjectId, code, name);
    }

    @Override
    public boolean update(Long subjectId, String code, String name) {
        return jdbcTemplate.update("UPDATE subjects " +
                "SET code = ?," +
                "name = ? " +
                "WHERE subjectId = ?", new Object[]{code, name, subjectId}) == 1;
    }

    @Override
    public boolean delete(Long subjectId) {
        return jdbcTemplate.update("DELETE FROM subjects WHERE subjectId = ?", new Object[]{subjectId}) == 1;
    }

    private static final RowMapper<Subject> SUBJECT_ROW_MAPPER = (rs, rowNum) ->
        new Subject(rs.getLong("subjectId"), rs.getString("code"),
                rs.getString("subjectName"));


    @Override
    public List<Subject> list() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT * FROM subjects" , SUBJECT_ROW_MAPPER));
    }
}
