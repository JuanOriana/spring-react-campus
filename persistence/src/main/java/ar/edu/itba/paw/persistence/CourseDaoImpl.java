package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CourseDaoImpl implements CourseDao {
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Course> ROW_MAPPER = (rs, rowNum) -> new Course(rs.getLong("subjectId"), rs.getInt("year"), rs.getString("code"), rs.getInt("quarter"), rs.getString("board"), rs.getString("name"));

    @Autowired
    public CourseDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  courses ( " +
                "subjectId INTEGER PRIMARY KEY, " +
                "name varchar (50), " +
                "code varchar(50), " +
                "quarter INTEGER , " +
                "board varchar(50), " +
                "year INTEGER )");
    }

    @Override
    public boolean create(Course course) {
        final Map<String, Object> args = new HashMap<>();
        args.put("subjectId", course.getSubjectId());
        args.put("name", course.getName());
        args.put("code", course.getCode());
        args.put("quarter", course.getQuarter());
        args.put("board", course.getBoard());
        args.put("year", course.getYear());

        final Number rowsAffected = jdbcInsert.execute(args);

        return rowsAffected.intValue() > 0;
    }

    @Override
    public boolean update(int id, Course course) {
        return jdbcTemplate.update("UPDATE courses " +
                "SET name = ?," +
                "year = ?," +
                "code = ?," +
                "quarter = ?," +
                "board = ?," +
                "WHERE subjectId = ?", new Object[]{course.getName(), course.getYear(), course.getCode(), course.getQuarter(), course.getBoard(), id}) == 1;

    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM courses WHERE subjectId = ?", new Object[]{id}) == 1;
    }

    @Override
    public List<Course> list() {
        // Only for testing, replace with proper db implementation
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM courses", ROW_MAPPER));
    }

    @Override
    public Optional<Course> getById(int id) {
        // Only for testing, replace with proper db implementation
        return jdbcTemplate.query("SELECT * FROM courses WHERE subjectId = ?", new Object[]{id}, ROW_MAPPER).stream().findFirst();
    }
}