package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserDao {
    User create(User user);
    boolean update(int userId, User user);
    boolean delete(int userId);
    Role getRole(int userId, int courseId);
    Optional<User> findById(int userId);
}
