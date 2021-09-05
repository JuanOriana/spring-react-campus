package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public boolean update(int fileNumber, User user) {
        return userDao.update(fileNumber, user);
    }

    @Override
    public boolean delete(int fileNumber) {
        return userDao.delete(fileNumber);
    }

    @Override
    public Role getRole(int fileNumber, int courseId) {
        return userDao.getRole(fileNumber, courseId);
    }

    @Override
    public User getByFileNumber(int fileNumber) {
        return userDao.getByFileNumber(fileNumber);
    }
}
