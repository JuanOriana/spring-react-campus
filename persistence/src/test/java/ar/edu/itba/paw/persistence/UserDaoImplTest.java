package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:populators/user_populator.sql")
@Rollback
@Transactional
public class UserDaoImplTest extends BasicPopulator {

    @Autowired
    private UserDao userDao;


    @Test
    public void testCreate() {
        User user = userDao.create(USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_IS_ADMIN);
        assertNotNull(user);
    }

    @Test
    public void testDelete() {
        assertTrue(userDao.delete(USER_ID));
    }

    @Test
    public void testDeleteInvalidId() {
        assertFalse(userDao.delete(USER_ID_INEXISTENCE));
    }

    @Test
    public void testFindById() {
        Optional<User> userOptional = userDao.findById(USER_ID);
        assertTrue(userOptional.isPresent());
        assertEquals(USER_FILE_NUMBER, userOptional.get().getFileNumber());
    }

    @Test
    public void testFindByIdInvalidId() {
        Optional<User> userOptional = userDao.findById(USER_ID_INEXISTENCE);
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
        assertTrue(userDao.update(USER_ID, updateUser));
    }

    @Test
    public void testGetRole() {
        Optional<Role> role = userDao.getRole(USER_ID, COURSE_ID);
        assertTrue(role.isPresent());
        assertEquals(STUDENT_ROLE_ID, role.get().getRoleId());
    }

    @Test
    public void testGetRoleInvalidCourse() {
        Optional<Role> role = userDao.getRole(USER_ID, 1337L); // In this case the course and the user are not releated
        assertFalse(role.isPresent());
    }

    @Test
    public void testGetProfileImage() {
        Optional<byte[]> image = userDao.getProfileImage(USER_ID);
        assertFalse(image.isPresent());
    }

    @Test
    public void testUpdateProfileImage() {
        File file = new File(FILE_PATH);
        try {
            byte[] bytea = Files.readAllBytes(file.toPath());
            boolean isUpdated = userDao.updateProfileImage(USER_ID, bytea);
            assertTrue(isUpdated);
        } catch (IOException ignored) {
        }
    }

    @Test
    public void testFindByUsername(){
        Optional<User> userOptional = userDao.findByUsername(USER_USERNAME);

        assertTrue(userOptional.isPresent());
        assertEquals(USER_ID, userOptional.get().getUserId());
    }

    @Test
    public void testFindByEmail(){
        Optional<User> userOptional = userDao.findByEmail(USER_EMAIL);

        assertTrue(userOptional.isPresent());
        assertEquals(USER_ID, userOptional.get().getUserId());
    }


    @Test
    public void testFindByFileNumber(){
        Optional<User> userOptional = userDao.findByFileNumber(USER_FILE_NUMBER);

        assertTrue(userOptional.isPresent());
        assertEquals(USER_ID, userOptional.get().getUserId());
    }

    @Test
    public void testGetMaxFileNumber(){
        assertEquals(USER_FILE_NUMBER, userDao.getMaxFileNumber());
    }

    @Test
    public void testList(){
        List<User> list = userDao.list();

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(USER_ID, list.get(0).getUserId());
    }



}
