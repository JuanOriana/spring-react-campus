package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.*;
import java.util.Optional;


public interface AnnouncementService {
    /**
     * Attempts to persist an announcement entry in the database
     * @param title of the announcement to be created
     * @param content of the announcement to be created
     * @param author of the announcement to be created
     * @param course of the announcement to be created
     * @return instance of the created announcement
     */
    Announcement create(String title, String content, User author, Course course);

    /**
     * Attempts to update a announcement
     *
     * @param id of the announcement to be modified
     * @param announcement modified announcement
     * @return true if the announcement was successfully updated, false otherwise
     */
    boolean update(Long id, Announcement announcement);

    /**
     * Attempts to delete an announcement
     *
     * @param id of the announcement to be deleted
     * @return true if the announcement was successfully removed, false otherwise
     */
    boolean delete(Long id);

    /**
     * Attempts to get an announcement given an id
     *
     * @param id of the announcement to be retrieved
     * @return the announcement corresponding to the given id if it exists, null otherwise
     */
    Optional<Announcement> getById(Long id);

    /**
     * Gets all the current available announcements for an user
     *
     * @param userId identifier of the user to get the announcements from
     * @return list containing all the current user available announcements (if any)
     */
    CampusPage<Announcement> listByUser(Long userId, Integer page, Integer pageSize);

    /**
     * Gets all the current available announcements for a specific course
     * @param courseId   identifier of the course to get the announcements from
     * @return list containing all the current course available announcements (if any)
     */
    CampusPage<Announcement> listByCourse(Long courseId, Integer page, Integer pageSize);
}
