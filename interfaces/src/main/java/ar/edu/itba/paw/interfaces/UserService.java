package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface UserService {
    /**
     * Creates a user entry in the database
     * @param user to be created
     * @return the user created
     */
    User create(User user);

    /**
     * Attempts to update the user entry
     * @param fileNumber of the user to be updated
     * @param user info to be updated
     * @return true if the user was updated successfully, false otherwise
     */
    boolean update(int fileNumber, User user);

    /**
     * Attempts to delete a user entry
     * @param fileNumber of the user to be deleted
     * @return true if the user was deleted successfully, false otherwise
     */
    boolean delete(int fileNumber);

    /**
     * Returns the role of the user for a certain course
     * @param fileNumber of the user to get the roles from
     * @param courseId of the course to get the roles from
     * @return role of the user for the specified course
     */
    Role getRole(int fileNumber, int courseId);

    /**
     * Gets a User based on its fileNumber
     * @param fileNumber of the user to get the data from
     * @return the user that has the given fileNumber
     */
    User getByFileNumber(int fileNumber);

}
