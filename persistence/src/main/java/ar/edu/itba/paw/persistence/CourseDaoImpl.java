package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) -> {
        return new Course(rs.getLong("courseId"), rs.getInt("year"),
                rs.getInt("quarter"), rs.getString("board"),
                new Subject(rs.getInt("subjectId"), rs.getString("code"), rs.getString("name")));
    };

    @Autowired
    public CourseDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses");
    }

    @Override
    public Course create(Course course) {
        final Map<String, Object> args = new HashMap<>();
        args.put("quarter", course.getQuarter());
        args.put("board", course.getBoard());
        args.put("year", course.getYear());
        args.put("subjectId", course.getSubject().getSubjectId());

        final int courseId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new Course(courseId, course.getYear(), course.getQuarter(), course.getBoard(), course.getSubject());
    }

    @Override
    public boolean update(long id, Course course) {
        return jdbcTemplate.update("UPDATE courses " +
                "SET subjectId = ?," +
                "year = ?," +
                "quarter = ?," +
                "board = ? " +
                "WHERE courseId = ?;", new Object[]{course.getSubject().getSubjectId(), course.getYear(), course.getQuarter(), course.getBoard(), id}) == 1;

    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM courses WHERE courseId = ?", new Object[]{id}) == 1;
    }

    @Override
    public List<Course> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM courses NATURAL JOIN subjects", COURSE_ROW_MAPPER));
    }

    @Override
    public Optional<Course> getById(long id) {
        return jdbcTemplate.query("SELECT * FROM courses NATURAL JOIN subjects WHERE courseId = ?", new Object[]{id}, COURSE_ROW_MAPPER).stream().findFirst();
    }

}