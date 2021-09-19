package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.*;
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
    private final SimpleJdbcInsert courseJdbcInsert;
    private final SimpleJdbcInsert userToCourseJdbcInsert;
    private static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) ->
        new Course.Builder()
            .withCourseId(rs.getLong("courseId"))
            .withYear(rs.getInt("year"))
            .withQuarter(rs.getInt("quarter"))
            .withBoard(rs.getString("board"))
            .withSubject(new Subject(rs.getLong("subjectId"), rs.getString("code"),
                    rs.getString("subjectName")))
            .build();

    @Autowired
    public CourseDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        courseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses").usingGeneratedKeyColumns("courseid");
        userToCourseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_to_course");
    }

    @Override
    public Course create(Integer year, Integer quarter, String board, Long subjectId, String subjectName,
                         String subjectCode) {
        final Map<String, Object> args = new HashMap<>();
        args.put("quarter", quarter);
        args.put("board", board);
        args.put("year", year);
        args.put("subjectId", subjectId);
        final Long courseId = courseJdbcInsert.executeAndReturnKey(args).longValue();
        return new Course.Builder()
                .withCourseId(courseId)
                .withYear(year)
                .withQuarter(quarter)
                .withBoard(board)
                .withSubject(new Subject(subjectId, subjectCode, subjectName))
                .build();
    }

    @Override
    public boolean enroll(Long userId, Long courseId, Integer roleId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("userId", userId);
        args.put("courseId", courseId);
        args.put("roleId", roleId);
        return userToCourseJdbcInsert.execute(args) > 0;
    }

    @Override
    public boolean update(Long id, Course course) {
        return jdbcTemplate.update("UPDATE courses " +
                "SET subjectId = ?," +
                "year = ?," +
                "quarter = ?," +
                "board = ? " +
                "WHERE courseId = ?;", new Object[]{course.getSubject().getSubjectId(), course.getYear(), course.getQuarter(), course.getBoard(), id}) == 1;

    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update("DELETE FROM courses WHERE courseId = ?", new Object[]{id}) == 1;
    }

    @Override
    public List<Course> list() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM courses NATURAL JOIN subjects", COURSE_ROW_MAPPER));
    }

    @Override
    public List<Course> list(Long userId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM courses NATURAL JOIN subjects " +
                "NATURAL JOIN user_to_course WHERE userId = ?", new Object[]{userId}, COURSE_ROW_MAPPER));
    }

    @Override
    public List<User> getStudents(Long courseId){
         return jdbcTemplate.query("SELECT * FROM users NATURAL JOIN user_to_course NATURAL JOIN roles WHERE courseId = ? AND roleId = ?", new Object[]{courseId, Permissions.STUDENT.getValue()},
                 LIST_RESULT_SET_EXTRACTOR_USERS);
    }

    @Override
    public Optional<Course> getById(Long id) {
        return jdbcTemplate.query("SELECT * FROM courses NATURAL JOIN subjects WHERE courseId = ?", new Object[]{id}, COURSE_ROW_MAPPER).stream().findFirst();
    }

    private static final ResultSetExtractor<Map<User, Role>> MAP_RESULT_SET_EXTRACTOR = (rs -> {
        Map<User, Role> result = new HashMap<>();
        while(rs.next()) {
            User user = new User.Builder()
                    .withUserId(rs.getLong("userId"))
                    .withFileNumber(rs.getInt("fileNumber"))
                    .withName(rs.getString("name"))
                    .withSurname(rs.getString("surname"))
                    .withUsername(rs.getString("username"))
                    .withEmail(rs.getString("email"))
                    .withPassword(rs.getString("password"))
                    .isAdmin(rs.getBoolean("isAdmin"))
                    .build();
            Role role = new Role(rs.getInt("roleId"), rs.getString("roleName"));
            result.put(user, role);
        }
        return result;
    });
   private static final ResultSetExtractor<List<User>> LIST_RESULT_SET_EXTRACTOR_USERS = (rs -> {
       List<User> result = new ArrayList<>();
        while(rs.next()) {
        User user = new User.Builder()
                .withUserId(rs.getLong("userId"))
                .withFileNumber(rs.getInt("fileNumber"))
                .withName(rs.getString("name"))
                .withSurname(rs.getString("surname"))
                .withUsername(rs.getString("username"))
                .withEmail(rs.getString("email"))
                .withPassword(rs.getString("password"))
                .isAdmin(rs.getBoolean("isAdmin"))
                .build();
        result.add(user);
    }
        return result;
});
    @Override
    public Map<User, Role> getTeachers(Long courseId) {
        return jdbcTemplate.query("SELECT * FROM users NATURAL JOIN user_to_course NATURAL JOIN roles WHERE " +
                "courseId = ? AND roleId BETWEEN ? AND ?", new Object[]{courseId, Permissions.HELPER.getValue(), Permissions.TEACHER.getValue()},
                MAP_RESULT_SET_EXTRACTOR);
    }

    private static final RowMapper<Integer> BELONGS_MAPPER = (rs, rowNum) -> rs.getInt("userId");

    @Override
    public boolean belongs(Long userId, Long courseId) {
        return jdbcTemplate.query("SELECT * FROM user_to_course WHERE courseId = ? AND userId = ?",
                new Object[]{courseId, userId}, BELONGS_MAPPER).stream().findFirst().isPresent();
    }

}