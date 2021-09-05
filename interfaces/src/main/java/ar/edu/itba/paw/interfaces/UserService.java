package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Creates a user entry in the database
     * @param user to be created
     * @return the user created
     */
    User create(User user);

    /**
     * Attempts to update the user entry
     * @param userId of the user to be updated
     * @param user info to be updated
     * @return true if the user was updated successfully, false otherwise
     */
    boolean update(int userId, User user);

    /**
     * Attempts to delete a user entry
     * @param userId of the user to be deleted
     * @return true if the user was deleted successfully, false otherwise
     */
    boolean delete(int userId);

    /**
     * Returns the role of the user for a certain course
     * @param userId of the user to get the roles from
     * @param courseId of the course to get the roles from
     * @return role of the user for the specified course
     */
    Role getRole(int userId, int courseId);

    /**
     * Gets a User based on its userId
     * @param userId of the user to get the data from
     * @return the user that has the given userId
     */
    Optional<User> findById(int userId);

}
