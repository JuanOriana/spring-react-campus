package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
public class UserDaoImplTest {
    private final Long USER_ID = 1L;
    private final Long USER_ID_INEXISTENCE = 999L;
    private final Integer USER_FILE_NUMBER = 1;
    private final String USER_NAME = "John";
    private final String USER_SURNAME = "Doe";
    private final String USER_USERNAME = "johndoe";
    private final String USER_EMAIL = "johndoe@lorem.com";
    private final String USER_PASSWORD = "d8d3aedd4b5d0ce0131600eaadc48dcb";
    private final String USER_UPDATE_NAME = "Alan";
    private final boolean IS_ADMIN = true;

    private final Long SUBJECT_ID = 1L;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "Protos";

    private final Long COURSE_ID = 1L;
    private final Integer COURSE_YEAR = 2021;
    private final Integer COURSE_QUARTER = 1;
    private final String COURSE_BOARD = "S1";


    private final Integer STUDENT_ROLE_ID = 1;
    private final String STUDENT_ROLE_NAME = "Estudiante";
    private final Integer TEACHER_ROLE_ID = 2;
    private final String TEACHER_ROLE_NAME = "Profesor";
    @Autowired
    UserDaoImpl userDao;
    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
            new User.Builder()
                    .withUserId(rs.getLong("userId"))
                    .withFileNumber(rs.getInt("fileNumber"))
                    .withName(rs.getString("name"))
                    .withSurname(rs.getString("surname"))
                    .withUsername(rs.getString("username"))
                    .withEmail(rs.getString("email"))
                    .withPassword(rs.getString("password"))
                    .isAdmin(rs.getBoolean("isAdmin"))
                    .build();

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

    private void insertRole(int roleId, String roleName) {
        SimpleJdbcInsert roleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("roles");
        Map<String, Object> args = new HashMap<>();
        args.put("roleId", roleId);
        args.put("roleName", roleName);
        roleJdbcInsert.execute(args);
    }
    private void insertUserToCourse(Long userId, Long courseId, int roleId) {
        SimpleJdbcInsert userToCourseJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user_to_course");
        Map<String, Object> args = new HashMap<>();
        args.put("userId",userId);
        args.put("courseId",courseId);
        args.put("roleId",roleId);
        userToCourseJdbcInsert.execute(args);
    }
    private void insertSubject(Long subjectId, String subjectName, String code) {
        SimpleJdbcInsert subjectJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("subjects");
        Map<String, Object> args = new HashMap<>();
        args.put("subjectId", subjectId);
        args.put("subjectName", subjectName);
        args.put("code", code);
        subjectJdbcInsert.execute(args);
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

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        insertUser(USER_ID, USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, IS_ADMIN);
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        User user = userDao.create(USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, IS_ADMIN);
        assertNotNull(user);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testDelete() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        assertTrue(userDao.delete(USER_ID));
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testDeleteInvalidId() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        assertFalse(userDao.delete(USER_ID_INEXISTENCE));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testFindById() {
        Optional<User> userOptional = userDao.findById(USER_ID);

        assertNotNull(userOptional);
        assertTrue(userOptional.isPresent());
        assertEquals(USER_NAME, userOptional.get().getName());
        assertEquals(USER_FILE_NUMBER, userOptional.get().getFileNumber());
        assertEquals(USER_EMAIL, userOptional.get().getEmail());
    }

    @Test
    public void testFindByIdInvalidId() {
        Optional<User> userOptional = userDao.findById(USER_ID_INEXISTENCE);

        assertNotNull(userOptional);
        assertFalse(userOptional.isPresent());
    }

    @Test
    public void testUpdate() {

        User updateUser =  new User.Builder()
                .withUserId(USER_ID)
                .withFileNumber(USER_FILE_NUMBER)
                .withName(USER_UPDATE_NAME)
                .withSurname(USER_SURNAME)
                .withUsername(USER_USERNAME)
                .withEmail(USER_EMAIL)
                .withPassword(USER_PASSWORD)
                .isAdmin(IS_ADMIN)
                .build();
        assertTrue(userDao.update(USER_ID, updateUser));

        List<User> userDbList = jdbcTemplate.query("SELECT * FROM users  WHERE userId=?",new Object[]{USER_ID},USER_ROW_MAPPER);

        assertFalse(userDbList.isEmpty());
        assertEquals(USER_ID, userDbList.get(0).getUserId());
        assertEquals(USER_UPDATE_NAME, userDbList.get(0).getName());



    }

    @Test
    public void testGetRole() {
        insertRole(STUDENT_ROLE_ID,STUDENT_ROLE_NAME);
        insertSubject(SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        insertUserToCourse(USER_ID,COURSE_ID,STUDENT_ROLE_ID);

        Optional<Role> role = userDao.getRole(USER_ID, COURSE_ID);
        assertNotNull(role);
        assertTrue(role.isPresent());
        assertEquals(STUDENT_ROLE_ID,role.get().getRoleId());
    }

    @Test
    public void testGetRoleInvalidCourse() {
        insertRole(STUDENT_ROLE_ID,STUDENT_ROLE_NAME);
        insertSubject(SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);

        Optional<Role> role = userDao.getRole(USER_ID, COURSE_ID); // In this case the course and the user are not releated
        assertNotNull(role);
        assertFalse(role.isPresent());
    }

    @Test
    public void testGetProfileImage() {
        Optional<byte[]> image = userDao.getProfileImage(USER_ID);
        assertNotNull(image);
        assertFalse(image.isPresent());
    }

    @Test
    public void testUpdateProfileImage() {
        File file = new File("src/test/resources/test.png");
        try {
            byte[] bytea = Files.readAllBytes(file.toPath());
            boolean isUpdated = userDao.updateProfileImage(USER_ID, bytea);
            assertTrue(isUpdated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetRoleWithCourses(){
        insertRole(STUDENT_ROLE_ID, STUDENT_ROLE_NAME);
        insertRole(TEACHER_ROLE_ID, TEACHER_ROLE_NAME);
        insertSubject(SUBJECT_ID,SUBJECT_NAME ,SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        insertUserToCourse(USER_ID, COURSE_ID, STUDENT_ROLE_ID);
        insertUserToCourse(USER_ID, COURSE_ID, TEACHER_ROLE_ID);
        Role studentRole = new Role.Builder().withRoleId(STUDENT_ROLE_ID).withRoleName(STUDENT_ROLE_NAME).build();
        Role teacherRole = new Role.Builder().withRoleId(TEACHER_ROLE_ID).withRoleName(TEACHER_ROLE_NAME).build();

        Map<Role, List<Course>> roleListMap = userDao.getRolesInCourses(USER_ID);

        assertNotNull(roleListMap);
        assertEquals(2, roleListMap.size());
        assertEquals(1, roleListMap.get(studentRole).size());
        assertEquals(1, roleListMap.get(teacherRole).size());
    }

}
