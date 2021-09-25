package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserDaoImplTest {
    private final Long USER_ID = 1L;
    private final Long USER_ID_INEXISTENCE = 999L;
    private final Integer FILE_NUMBER = 1;
    private final String NAME = "John";
    private final String SURNAME = "Doe";
    private final String USERNAME = "johndoe";
    private final String EMAIL = "johndoe@lorem.com";
    private final String PASSWORD = "d8d3aedd4b5d0ce0131600eaadc48dcb";
    private final boolean IS_ADMIN = true;
    private final Integer STUDENT_ROLE_ID = 1;
    private final String STUDENT_ROLE_NAME = "Estudiante";
    private final Integer TEACHER_ROLE_ID = 2;
    private final String TEACHER_ROLE_NAME = "Profesor";
    private final String sqlInsertUserWithId = String.format("INSERT INTO users (userId,fileNumber,name,surname,username,email,password,isAdmin) VALUES (%d,%d,'%s','%s','%s','%s','%s',%s)", USER_ID, FILE_NUMBER, NAME, SURNAME, USERNAME, EMAIL, PASSWORD, IS_ADMIN);
    @Autowired
    UserDaoImpl userDao;
    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        User user = userDao.create(FILE_NUMBER, NAME, SURNAME, USERNAME, EMAIL, PASSWORD, IS_ADMIN);
        assertNotNull(user);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testDelete() {
        jdbcTemplate.execute(sqlInsertUserWithId);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        assertTrue(userDao.delete(USER_ID));
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testDeleteInvalidId() {
        jdbcTemplate.execute(sqlInsertUserWithId);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        assertFalse(userDao.delete(USER_ID_INEXISTENCE));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testFindById() {
        jdbcTemplate.execute(sqlInsertUserWithId);
        Optional<User> userOptional = userDao.findById(USER_ID);

        assertNotNull(userOptional);
        assertTrue(userOptional.isPresent());
        assertEquals(NAME, userOptional.get().getName());
        assertEquals(FILE_NUMBER, userOptional.get().getFileNumber());
        assertEquals(EMAIL, userOptional.get().getEmail());
    }

    @Test
    public void testFindByIdInvalidId() {
        jdbcTemplate.execute(sqlInsertUserWithId);
        Optional<User> userOptional = userDao.findById(USER_ID_INEXISTENCE);

        assertNotNull(userOptional);
        assertFalse(userOptional.isPresent());
    }

    @Test
    public void testUpdate() {
        jdbcTemplate.execute(sqlInsertUserWithId);
        assertTrue(userDao.update(USER_ID, new User.Builder()
                .withUserId(USER_ID)
                .withFileNumber(FILE_NUMBER)
                .withName(NAME)
                .withSurname(SURNAME)
                .withUsername(USERNAME)
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .isAdmin(IS_ADMIN)
                .build()));

    }

    @Test
    public void testGetRole() {
        // TODO finish this test with new implemetation
//        jdbcTemplate.execute(sqlInsertUserWithId);
//        String sqlInsertRoleWithId = String.format("INSERT INTO roles (roleId,roleName) VALUES (%d,'%s');", STUDENT_ROLE_ID,STUDENT_ROLE_NAME);
//        String sqlInsertUserToRole = String.format("INSERT INTO user_to_role (userId,roleId) VALUES (%d,%d);", USER_ID,STUDENT_ROLE_ID);
//        jdbcTemplate.execute(sqlInsertRoleWithId);
//        jdbcTemplate.execute(sqlInsertUserToRole);
//
//        Role role = userDao.getRole(USER_ID,);
    }

    @Test
    public void testGetProfileImage() {
        Optional<byte[]> image = userDao.getProfileImage(USER_ID);
        assertNotNull(image);
        assertFalse(image.isPresent());
    }

    @Test
    public void testUpdateProfileImage() {
        jdbcTemplate.execute(sqlInsertUserWithId);
        String sqlInsertProfileImageRow = String.format("INSERT INTO profile_images (image,userId) VALUES (null,%d)", USER_ID);
        jdbcTemplate.execute(sqlInsertProfileImageRow);
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
        int courseId = 2,subjectId=3,quarter=1,year=2021;
        String insertSubjectSql = String.format("INSERT INTO subjects (subjectId,code,subjectName) VALUES (%d,'A1','PAW')", subjectId);
        String insertCourseWithIdSql = String.format("INSERT INTO courses  VALUES (%d, %d, %d, 'S1',%d)", courseId, subjectId, quarter, year);
        String insertUserToCourseSql = String.format("INSERT INTO user_to_course VALUES (%d,%d,%d)", courseId, USER_ID, STUDENT_ROLE_ID);
        String insertUserToCourseSql2 = String.format("INSERT INTO user_to_course VALUES (%d,%d,%d)", courseId, USER_ID, TEACHER_ROLE_ID);
        String sqlInsertRoleWithId = String.format("INSERT INTO roles (roleId,roleName) VALUES (%d,'%s')", STUDENT_ROLE_ID,STUDENT_ROLE_NAME);
        String sqlInsertRoleWithId2 = String.format("INSERT INTO roles (roleId,roleName) VALUES (%d,'%s')", TEACHER_ROLE_ID,TEACHER_ROLE_NAME);
        jdbcTemplate.execute(sqlInsertUserWithId);
        jdbcTemplate.execute(sqlInsertRoleWithId);
        jdbcTemplate.execute(sqlInsertRoleWithId2);
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertCourseWithIdSql);
        jdbcTemplate.execute(insertUserToCourseSql);
        jdbcTemplate.execute(insertUserToCourseSql2);
        Role studentRole = new Role(STUDENT_ROLE_ID,STUDENT_ROLE_NAME);
        Role teacherRole = new Role(TEACHER_ROLE_ID,TEACHER_ROLE_NAME);

        Map<Role, List<Course>> roleListMap = userDao.getRolesInCourses(USER_ID);

        assertNotNull(roleListMap);
        assertEquals(2, roleListMap.size());
        assertEquals(1, roleListMap.get(studentRole).size());
        assertEquals(1, roleListMap.get(teacherRole).size());
    }

}
