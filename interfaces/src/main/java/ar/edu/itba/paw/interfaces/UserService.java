package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import java.util.List;
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
     */
    void updateProfileImage(Long userId, byte[] image);

}
