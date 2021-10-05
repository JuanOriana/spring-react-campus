package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class UserDaoImplTest extends BasicPopulator {
    @Autowired
    UserDaoImpl userDao;

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

    @Before
    public void setUp() {
        super.setUp();
        insertUser(USER_ID, USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_IS_ADMIN);
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        User user = userDao.create(USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_IS_ADMIN);
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

        User updateUser = new User.Builder()
                .withUserId(USER_ID)
                .withFileNumber(USER_FILE_NUMBER)
                .withName(USER_UPDATE_NAME)
                .withSurname(USER_SURNAME)
                .withUsername(USER_USERNAME)
                .withEmail(USER_EMAIL)
                .withPassword(USER_PASSWORD)
                .isAdmin(USER_IS_ADMIN)
                .build();
        assertTrue(userDao.update(USER_ID, updateUser));

        List<User> userDbList = jdbcTemplate.query("SELECT * FROM users  WHERE userId=?", new Object[]{USER_ID}, USER_ROW_MAPPER);

        assertFalse(userDbList.isEmpty());
        assertEquals(USER_ID, userDbList.get(0).getUserId());
        assertEquals(USER_UPDATE_NAME, userDbList.get(0).getName());


    }

    @Test
    public void testGetRole() {
        insertRole(STUDENT_ROLE_ID, STUDENT_ROLE_NAME);
        insertSubject(SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        insertUserToCourse(USER_ID, COURSE_ID, STUDENT_ROLE_ID);

        Optional<Role> role = userDao.getRole(USER_ID, COURSE_ID);
        assertNotNull(role);
        assertTrue(role.isPresent());
        assertEquals(STUDENT_ROLE_ID, role.get().getRoleId());
    }

    @Test
    public void testGetRoleInvalidCourse() {
        insertRole(STUDENT_ROLE_ID, STUDENT_ROLE_NAME);
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

}
