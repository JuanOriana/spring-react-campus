package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.SystemUnavailableException;
import ar.edu.itba.paw.models.exception.DuplicateUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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
    public User create(Integer fileNumber, String name, String surname, String username,
                       String email, String password, boolean isAdmin) {
        User user = null;
        try {
            user = userDao.create(fileNumber, name, surname, username,
                    email, passwordEncoder.encode(password), isAdmin);
        } catch (DuplicateKeyException dke) {
            throw new DuplicateUserException.Builder()
                    .withError(dke.getMessage())
                    .withUsername(username)
                    .withSurname(surname)
                    .withEmail(email)
                    .withName(name)
                    .withFileNumber(fileNumber)
                    .build();
        } catch (DataAccessException dae) {
            throw new SystemUnavailableException(dae.getMessage());
        }
        return user;
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

    @Transactional(readOnly = true)
    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        return userDao.getProfileImage(userId);
    }

    @Transactional
    @Override
    public void updateProfileImage(Long userId, byte[] image) {
        userDao.updateProfileImage(userId, image);
    }
}
