package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public boolean update(int userId, User user) {
        return userDao.update(userId, user);
    }

    @Override
    public boolean delete(int userId) {
        return userDao.delete(userId);
    }

    @Override
    public Role getRole(int userId, int courseId) {
        return userDao.getRole(userId, courseId);
    }

    @Override
    public Optional<User> findById(int userId) {
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
