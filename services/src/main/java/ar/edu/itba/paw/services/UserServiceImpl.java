package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.DuplicateUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User create(Integer fileNumber, String name, String surname, String username,
                       String email, String password, boolean isAdmin) {
        boolean matchUsername = userDao.findByUsername(username).isPresent();
        boolean matchEmail = userDao.findByEmail(email).isPresent();
        boolean matchFileNumber = userDao.findByFileNumber(fileNumber).isPresent();
        if(matchUsername || matchEmail || matchFileNumber) {
            DuplicateUserException due = new DuplicateUserException.Builder()
                    .withName(name)
                    .withSurname(surname)
                    .withUsername(username)
                    .withEmail(email)
                    .withFileNumber(fileNumber)
                    .build();
            due.setEmailTaken(matchEmail);
            due.setFileNumberTaken(matchFileNumber);
            due.setUsernameTaken(matchUsername);
            throw due;
        }
        return userDao.create(fileNumber, name, surname, username,
                email, passwordEncoder.encode(password), isAdmin);
    }

    @Transactional
    @Override
    public boolean update(Long userId, User user) {
        return userDao.update(userId, user);
    }

    @Transactional
    @Override
    public boolean delete(Long userId) {
        return userDao.delete(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long userId) {
        return userDao.findById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> list() {
        return userDao.list();
    }

    @Override
    public CampusPage<User> list(Integer page, Integer pageSize) {
        return userDao.list(new CampusPageRequest(page, pageSize));
    }

    @Override
    public CampusPage<User> filterByCourse(Long courseId, Integer page, Integer pageSize) {
        return userDao.filterByCourse(courseId, new CampusPageRequest(page, pageSize));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        return userDao.getProfileImage(userId);
    }

    @Transactional
    @Override
    public boolean updateProfileImage(Long userId, byte[] image) {
        return userDao.updateProfileImage(userId, image);
    }

    @Override
    public Integer getMaxFileNumber() {
        return userDao.getMaxFileNumber();
    }

    @Override
    public CampusPage<User> getStudentsByCourse(Long courseId, Integer page, Integer pageSize) {
        return userDao.getStudentsByCourse(courseId, new CampusPageRequest(page, pageSize));
    }


}
