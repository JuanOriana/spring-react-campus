package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;
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
     * Gets all the current available announcements for a specific course
     *
     * @param courseId identifier of the course to get the announcements from
     * @return list containing all the current course available announcements (if any)
     */
    List<Announcement> listByCourse(Long courseId);

    /**
     * Gets all the current available announcements for a specific course
     *
     * @param courseId   identifier of the course to get the announcements from
     * @param comparator identifies the order expected in the response list
     * @return list containing all the current course available announcements (if any)
     */
    List<Announcement> listByCourse(Long courseId, Comparator<Announcement> comparator);

    /**
     * Attempts to get an announcement given an id
     *
     * @param id of the announcement to be retrieved
     * @return the announcement corresponding to the given id if it exists, null otherwise
     */
    Optional<Announcement> getById(Long id);


    Page<Announcement> findAnnouncementByPage(Long userId, Pageable pageable);

    boolean isPaginationValid(Long userId, Integer page, Integer pageSize);
}
