package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    /**
     * Persist a user entry in the database
     *
     * @param fileNumber unique real life id of the user
     * @param name       of the user
     * @param surname    of the user
     * @param username   of the user
     * @param email      of the user
     * @param password   of the user
     * @param isAdmin    determines if the user is a full-fledged admin
     * @return the generated User instance if it was created successfully, a collection of errors if any key was violated
     */
    User create(Integer fileNumber, String name, String surname, String username, String email, String password,
                                            boolean isAdmin);

    /**
     * Attempts to update the user entry
     *
     * @param userId of the user to be updated
     * @param user   info to be updated
     * @return true if the user was updated successfully, false otherwise
     */
    boolean update(Long userId, User user);

    /**
     * Attempts to delete a user entry
     *
     * @param userId of the user to be deleted
     * @return true if the user was deleted successfully, false otherwise
     */
    boolean delete(Long userId);

    /**
     * Returns the role of the user for a certain course
     *
     * @param userId   of the user to get the roles from
     * @param courseId of the course to get the roles from
     * @return role of the user for the specified course
     */
    Optional<Role> getRole(Long userId, Long courseId);

    /**
     * Gets a User based on its userId
     *
     * @param userId of the user to get the data from
     * @return the user that has the given userId
     */
    Optional<User> findById(Long userId);

    /**
     * Gets a user based on its username
     *
     * @param username to look for
     * @return the user that has the given username
     */
    Optional<User> findByUsername(String username);

    Optional<User> findByFileNumber(Integer fileNumber);

    Optional<User> findByEmail(String email);

    /**
     * Gets all the users (if any)
     *
     * @return list containing all the available users (if any)
     */
    List<User> list();


    /**
     * Attempts to get user profile image (if any). By default none image is set
     *
     * @return an optional of byte[] that represents the image. If none, optional.isPresent == false
     */
    Optional<byte[]> getProfileImage(Long userId);

    /**
     * Attempts to update user profile image
     *
     * @return true if the user image was updated successfully, false otherwise
     */
    boolean updateProfileImage(Long userId, byte[] image);

    /**
     *  Attempts to get all the courses where the user has a role.
     * @param userId of the user
     * @return a map that for each role that the user has in any course with the corresponding list of all the courses where the user has that role
     */
    Map<Role,List<Course>> getRolesInCourses(Long userId);

}
