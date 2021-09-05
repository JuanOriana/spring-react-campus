package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public boolean update(int fileNumber, User user) {
        return false;
    }

    @Override
    public boolean delete(int fileNumber) {
        return false;
    }

    @Override
    public Role getRole(int fileNumber, int courseId) {
        return null;
    }

    @Override
    public User getByFileNumber(int fileNumber) {
        return null;
    }
}
