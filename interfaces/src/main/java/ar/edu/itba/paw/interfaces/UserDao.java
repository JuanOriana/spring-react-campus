package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserDao {
    User create(Integer fileNumber, String name, String surname, String username, String email, String password,
                boolean isAdmin);
    boolean update(int userId, User user);
    boolean delete(int userId);
    Optional<Role> getRole(int userId, int courseId);
    Optional<User> findById(int userId);
    Optional<User> findByUsername(String username);
}
