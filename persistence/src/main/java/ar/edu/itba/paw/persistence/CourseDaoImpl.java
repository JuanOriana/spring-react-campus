package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CourseDaoImpl implements CourseDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private static final RowMapper<Course> ROW_MAPPER = (rs, rowNum) -> {
        Course course = new Course(rs.getInt("year"), rs.getString("code"), rs.getInt("quarter"), rs.getString("board"), rs.getString("name"));
        course.setCourseId(rs.getLong("courseId"));
        return course;
    };

    @Autowired
    public CourseDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses");
    }

    @Override
    public boolean create(Course course) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", course.getName());
        args.put("code", course.getCode());
        args.put("quarter", course.getQuarter());
        args.put("board", course.getBoard());
        args.put("year", course.getYear());

        Number rowsAffected;
        try {
            rowsAffected = jdbcInsert.execute(args);
        } catch (DuplicateKeyException e) {
            return false;
        }


        return rowsAffected.intValue() > 0;
    }

    @Override
    public boolean update(long id, Course course) {
        return jdbcTemplate.update("UPDATE courses " +
                "SET name = ?," +
                "year = ?," +
                "code = ?," +
                "quarter = ?," +
                "board = ? " +
                "WHERE courseId = ?;", new Object[]{course.getName(), course.getYear(), course.getCode(), course.getQuarter(), course.getBoard(), id}) == 1;

    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM courses WHERE courseId = ?", new Object[]{id}) == 1;
    }

    @Override
    public List<Course> list() {
        // Only for testing, replace with proper db implementation
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM courses", ROW_MAPPER));
    }

    @Override
    public Optional<Course> getById(long id) {
        // Only for testing, replace with proper db implementation
        return jdbcTemplate.query("SELECT * FROM courses WHERE courseId = ?", new Object[]{id}, ROW_MAPPER).stream().findFirst();
    }
}