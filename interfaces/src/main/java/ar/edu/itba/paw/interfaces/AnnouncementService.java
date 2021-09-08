package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.Announcement;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    /**
     * Attempts to persist an announcement entry in the database
     *
     * @param announcement The announcement to be persisted in the database
     * @return the announcement if it was successfully added
     */
    Announcement create(Announcement announcement);

    /**
     * Attempts to update a announcement
     *
     * @param id           of the announcement to be modified
     * @param announcement modified announcement
     * @return true if the announcement was successfully updated, false otherwise
     */
    boolean update(long id, Announcement announcement);

    /**
     * Attempts to delete an announcement
     *
     * @param id of the announcement to be deleted
     * @return true if the announcement was successfully removed, false otherwise
     */
    boolean delete(long id);

    /**
     * Gets all the current available announcements
     * @param page offset to be retrieved
     * @param pageSize size of the page to be retrieved
     * @return list containing all the current available announcements (if any)
     */
    List<Announcement> list(long page, long pageSize);

    /**
     * Gets the amount of pages based on the page size
     * @param pageSize size of a page from a list query
     * @return amount of pages for the given page size
     */
    int getPageCount(long pageSize);

    /**
     * Returns an arbitrary ordered list of announcements with pagination
     * @param page offset to be retrieved
     * @param pageSize size of the page to be retrieved
     * @param comparator identifies the order expected in the response list
     * @return list of announcements of size pageSize (or smaller)
     */
    List<Announcement> list(long page, long pageSize, Comparator<Announcement> comparator);

    /**
     * Gets all the current available announcements for a specific course
     *
     * @param courseId identifier of the course to get the announcements from
     * @return list containing all the current course available announcements (if any)
     */
    List<Announcement> listByCourse(long courseId);

    /**
     * Gets all the current available announcements for a specific course
     *
     * @param courseId   identifier of the course to get the announcements from
     * @param comparator identifies the order expected in the response list
     * @return list containing all the current course available announcements (if any)
     */
    List<Announcement> listByCourse(long courseId, Comparator<Announcement> comparator);

    /**
     * Attempts to get an announcement given an id
     *
     * @param id of the announcement to be retrieved
     * @return the announcement corresponding to the given id if it exists, null otherwise
     */
    Optional<Announcement> getById(long id);
}
