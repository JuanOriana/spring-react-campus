package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Either<User, Collection<Errors>> create(Integer fileNumber, String name, String surname, String username, String email, String password,
                                                   boolean isAdmin) {
        boolean existsUsername = findByUsername(username).isPresent();
        boolean existsFileNumber = findByFileNumber(fileNumber).isPresent();
        boolean existsEmail = findByEmail(email).isPresent();
        if(existsEmail || existsFileNumber || existsUsername) {
            Collection<Errors> errors = new ArrayList<>();
            if(existsEmail) errors.add(Errors.MAIL_ALREADY_IN_USE);
            if(existsFileNumber) errors.add(Errors.FILE_NUMBER_ALREADY_IN_USE);
            if(existsUsername) errors.add(Errors.USERNAME_ALREADY_IN_USE);
            return Either.alternative(errors);
        }
        return Either.value(userDao.create(fileNumber, name, surname, username,
                email, passwordEncoder.encode(password), isAdmin));
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

    @Override
    public Optional<Role> getRole(Long userId, Long courseId) {
        return userDao.getRole(userId, courseId);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Optional<User> findByFileNumber(Integer fileNumber) {
        return userDao.findByFileNumber(fileNumber);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> list() {
        return userDao.list();
    }

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
    public Map<Role, List<Course>> getRolesInCourses(Long userId) {
        return userDao.getRolesInCourses(userId);
    }
}
