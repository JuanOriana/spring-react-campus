package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
                new Subject(rs.getInt("subjectId"), rs.getString("code"), rs.getString("subjectName")));
    };

    private enum ROLES { STUDENT(1), HELPER(2), TEACHER(3);
        private final int id;
        ROLES(int id) {this.id = id;}
        public int getValue() { return id; }
    };

    @Autowired
    public CourseDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses").usingGeneratedKeyColumns("courseid");
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

    private static final ResultSetExtractor<Map<User, Role>> MAP_RESULT_SET_EXTRACTOR = (rs -> {
        Map<User, Role> result = new HashMap<>();
        while(rs.next()) {
            User user = new User(rs.getInt("userId"), rs.getInt("fileNumber"),
                    rs.getString("name"), rs.getString("surname"),
                    rs.getString("username"), rs.getString("email"), null, false);
            Role role = new Role(rs.getInt("roleId"), rs.getString("roleName"));
            result.put(user, role);
        }
        return result;
    });

    @Override
    public Map<User, Role> getTeachers(long courseId) {
        return jdbcTemplate.query("SELECT * FROM users NATURAL JOIN user_to_course NATURAL JOIN roles WHERE " +
                "courseId = ? AND roleId BETWEEN ? AND ?", new Object[]{courseId, ROLES.HELPER.getValue(), ROLES.TEACHER.getValue()},
                MAP_RESULT_SET_EXTRACTOR);
    }

}