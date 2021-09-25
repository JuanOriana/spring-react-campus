package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {
    User create(Integer fileNumber, String name, String surname, String username, String email, String password,
                boolean isAdmin);

    boolean update(Long userId, User user);

    boolean delete(Long userId);

    Optional<Role> getRole(Long userId, Long courseId);

    Optional<User> findById(Long userId);

    Optional<User> findByUsername(String username);

    List<User> list();

    Optional<byte[]> getProfileImage(Long userId);

    boolean updateProfileImage(Long userId, byte[] image);

    Map<Role,List<Course>> getRolesInCourses(Long userId);
}
