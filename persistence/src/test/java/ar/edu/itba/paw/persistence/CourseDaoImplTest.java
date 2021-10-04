package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class CourseDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private CourseDaoImpl courseDao;

    private JdbcTemplate jdbcTemplate;

    private final Long COURSE_ID = 1L;
    private final Integer QUARTER = 1;
    private final Integer UPDATE_QUARTER = 2;
    private final Integer YEAR = 2021;
    private final String BOARD = "S1";
    private final Long INVALID_COURSE_ID = 999L;

    private final String SUBJECT_NAME = "PAW";
    private final String SUBJECT_CODE = "A1";
    private final Long SUBJECT_ID = 1L;

    private final Long USER_ID = 1L;
    private final Integer USER_FILE_NUMBER = 41205221;
    private final String USER_NAME = "Paw";
    private final String USER_SURNAME = "2021";
    private final String USER_USERNAME = "paw2021";
    private final String USER_EMAIL = "paw2021@itba.edu.ar";
    private final String USER_PASSWORD = "asd123";
    private final boolean USER_IS_ADMIN = true;

    private final Integer STUDENT_ROLE_ID = 1;
    private final Integer TEACHER_ROLE_ID = 3;
    private final String  TEACHER_ROLE_NAME = "Teacher";
    private final String STUDENT_ROLE_NAME = "Student";

    private static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) ->
            new Course.Builder()
                    .withCourseId(rs.getLong("courseId"))
                    .withYear(rs.getInt("year"))
                    .withQuarter(rs.getInt("quarter"))
                    .withBoard(rs.getString("board"))
                    .withSubject(new Subject(rs.getLong("subjectId"), rs.getString("code"),
                            rs.getString("subjectName")))
                    .build();

    private static final RowMapper<Role> USER_TO_COURSE_ROW_MAPPER = (rs,rowNum)->
                new Role.Builder().withRoleId(rs.getInt("roleid")).withRoleName(rs.getString("rolename")).build();

    private void insertSubject(Long subjectId, String code, String subjectName){
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("subjects");
        Map<String,Object> args = new HashMap<>();
        args.put("subjectId",subjectId);
        args.put("code", code);
        args.put("subjectName", subjectName);
        subjectJdbcInsert.execute(args);
    }

    private void insertUserToCourse(Long courseId,Long userId,Integer roleId){
        SimpleJdbcInsert userToCourseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_to_course");
        Map<String,Object> args = new HashMap<>();
        args.put("courseId",courseId);
        args.put("userId", userId);
        args.put("roleId", roleId);
        userToCourseJdbcInsert.execute(args);
    }

    private void insertUser(Long userId, int fileNumber, String name, String surname, String username, String email,
                            String password, boolean isAdmin) {
        SimpleJdbcInsert userJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
        SimpleJdbcInsert profileImageJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("profile_images");
        Map<String, Object> args = new HashMap<>();
        args.put("userId",userId);
        args.put("fileNumber",fileNumber);
        args.put("name",name);
        args.put("surname",surname);
        args.put("username",username);
        args.put("email",email);
        args.put("password",password);
        args.put("isAdmin",isAdmin);
        userJdbcInsert.execute(args);
        Map<String,Object> argsProfileImage = new HashMap<>();
        argsProfileImage.put("image", null);
        argsProfileImage.put("userid", userId);
        profileImageJdbcInsert.execute(argsProfileImage);
    }

    private void insertCourse(Long courseId, Long subjectId, int quarter, String board, int year) {
        SimpleJdbcInsert courseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("courses");
        Map<String, Object> args = new HashMap<>();
        args.put("courseId", courseId);
        args.put("subjectId", subjectId);
        args.put("quarter", quarter);
        args.put("board", board);
        args.put("year", year);
        courseJdbcInsert.execute(args);
    }

    private void insertRole(int roleId, String roleName) {
        SimpleJdbcInsert roleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
        Map<String, Object> args = new HashMap<>();
        args.put("roleId", roleId);
        args.put("roleName", roleName);
        roleJdbcInsert.execute(args);
    }

    private User getMockUser(){
        return new User.Builder()
                .withUserId(USER_ID)
                .withFileNumber(USER_FILE_NUMBER)
                .withName(USER_NAME)
                .withSurname(USER_SURNAME)
                .withUsername(USER_USERNAME)
                .withEmail(USER_EMAIL)
                .withPassword(USER_PASSWORD)
                .isAdmin(USER_IS_ADMIN)
                .build();
    }

    private Role getMockRole(){
        return new Role.Builder().withRoleId(TEACHER_ROLE_ID).withRoleName(TEACHER_ROLE_NAME).build();
    }

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        insertSubject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        insertUser(USER_ID, USER_FILE_NUMBER,USER_NAME , USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_IS_ADMIN);
        insertRole(STUDENT_ROLE_ID, STUDENT_ROLE_NAME);
        insertCourse(COURSE_ID, SUBJECT_ID, QUARTER, BOARD, YEAR);
        insertUserToCourse(COURSE_ID, USER_ID, STUDENT_ROLE_ID);
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        Course course = courseDao.create(YEAR, QUARTER, BOARD, SUBJECT_ID);
        assertNotNull(course);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateDuplicateUniqueValues() {
        final Course isCreated1 = courseDao.create(YEAR, QUARTER, BOARD, SUBJECT_ID);
        final Course isCreated2 = courseDao.create(YEAR, QUARTER, BOARD, SUBJECT_ID);
        Assert.fail("Should have thrown Runtime Exception for duplicate constraint");
    }

    @Test
    public void testDelete() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
        courseDao.delete(COURSE_ID);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test
    public void testDeleteNoExist() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
        assertFalse(courseDao.delete(INVALID_COURSE_ID));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test
    public void testGetById() {
        final Optional<Course> course = courseDao.getById(COURSE_ID);
        assertNotNull(course);
        assertTrue(course.isPresent());
        assertEquals(COURSE_ID, course.get().getCourseId());
    }

    @Test
    public void testGetByIdNoExist() {
        final Optional<Course> course = courseDao.getById(INVALID_COURSE_ID);
        assertNotNull(course);
        assertFalse(course.isPresent());
    }

    @Test
    public void testList() {
        final List<Course> list = courseDao.list(USER_ID);
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testEmptyList() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        final List<Course> list = courseDao.list(USER_ID);
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void testUpdate(){
        Course course = new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(YEAR)
                .withQuarter(UPDATE_QUARTER)
                .withBoard(BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
        assertTrue(courseDao.update(COURSE_ID,course));

        Course courseDb = jdbcTemplate.query("SELECT * FROM courses NATURAL JOIN subjects WHERE courseId = ?",new Object[]{COURSE_ID},COURSE_ROW_MAPPER).get(0);
        assertNotNull(courseDb);
        assertEquals(COURSE_ID, courseDb.getCourseId());
        assertEquals(UPDATE_QUARTER, courseDb.getQuarter());
        assertEquals(BOARD, courseDb.getBoard());
    }

    @Test
    public void testGetStudents(){
        List<User> list = courseDao.getStudents(COURSE_ID);

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(USER_ID, list.get(0).getUserId());
    }

    @Test
    public void testGetTeachers(){
        insertRole(TEACHER_ROLE_ID, TEACHER_ROLE_NAME);
        insertUserToCourse(COURSE_ID, USER_ID, TEACHER_ROLE_ID);
        Map<User,Role> userRoleMap = courseDao.getTeachers(COURSE_ID);
        User user = getMockUser();
        Role teacherRole = getMockRole();
        assertNotNull(userRoleMap);
        assertEquals(teacherRole, userRoleMap.get(user));

    }

    @Test
    public void testEnroll(){
        assertTrue(courseDao.enroll(USER_ID, COURSE_ID, STUDENT_ROLE_ID));
        Role role = jdbcTemplate.query("SELECT * FROM user_to_course NATURAL JOIN roles WHERE userid=? AND courseid = ?",new Object[]{USER_ID,COURSE_ID},USER_TO_COURSE_ROW_MAPPER).get(0);
        assertNotNull(role);
        assertEquals(STUDENT_ROLE_ID,role.getRoleId());
    }

}
