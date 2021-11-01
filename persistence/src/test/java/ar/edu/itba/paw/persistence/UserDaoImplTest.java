package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.RoleDao;
import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:populators/user_populator.sql")
@Rollback
@Transactional
public class UserDaoImplTest extends BasicPopulator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImplTest.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private CourseDao courseDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testCreate() {
        User user = userDao.create(USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_IS_ADMIN);
        assertNotNull(user);
    }

    @Test
    public void testDelete() {
        assertTrue(userDao.delete(1337L));
    }

    @Test
    public void testDeleteInvalidId() {
        assertFalse(userDao.delete(USER_ID_INEXISTENCE));
    }

    @Test
    public void testFindById() {
        Optional<User> userOptional = userDao.findById(1337L);
        assertNotNull(userOptional);
        assertTrue(userOptional.isPresent());
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
                .withFileNumber(USER_FILE_NUMBER)
                .withName(USER_UPDATE_NAME)
                .withSurname(USER_SURNAME)
                .withUsername(USER_USERNAME)
                .withEmail(USER_EMAIL)
                .withPassword(USER_PASSWORD)
                .isAdmin(USER_IS_ADMIN)
                .build();
        assertTrue(userDao.update(1337L, updateUser));
    }

    @Test
    public void testGetRole() {
        Optional<Role> role = userDao.getRole(1337L, 1L);
        assertNotNull(role);
        assertTrue(role.isPresent());
        assertEquals(STUDENT_ROLE_ID, role.get().getRoleId());
    }

    @Test
    public void testGetRoleInvalidCourse() {
        Optional<Role> role = userDao.getRole(USER_ID, 1337L); // In this case the course and the user are not releated
        assertNotNull(role);
        assertFalse(role.isPresent());
    }

    @Test
    public void testGetProfileImage() {
        Optional<byte[]> image = userDao.getProfileImage(1337L);
        assertNotNull(image);
        assertFalse(image.isPresent());
    }

    @Test
    public void testUpdateProfileImage() {
        File file = new File("src/test/resources/test.png");
        try {
            byte[] bytea = Files.readAllBytes(file.toPath());
            boolean isUpdated = userDao.updateProfileImage(1337L, bytea);
            assertTrue(isUpdated);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
