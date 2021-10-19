package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class AnnouncementDaoJpa extends BasePaginationDaoImpl<Announcement> implements AnnouncementDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Announcement create(LocalDateTime date, String title, String content, User author, Course course) {
        final Announcement announcement = new Announcement.Builder()
                .withDate(date)
                .withTitle(title)
                .withContent(content)
                .withAuthor(author)
                .withCourse(course)
                .build();
        em.persist(announcement);
        return announcement;
    }

    @Transactional
    @Override
    public boolean update(Long id, Announcement announcement) {
        Optional<Announcement> dbAnnouncement = findById(id);
        if(!dbAnnouncement.isPresent()) return false;
        dbAnnouncement.get().merge(announcement);
        em.flush();
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Optional<Announcement> dbAnnouncement = findById(id);
        if(!dbAnnouncement.isPresent()) return false;
        em.remove(dbAnnouncement.get());
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Announcement> findById(Long id) {
        return Optional.ofNullable(em.find(Announcement.class, id));
    }


    @Transactional(readOnly = true)
    @Override
    public CampusPage<Announcement> listByUser(Long userId, CampusPageRequest pageRequest)
            throws PaginationArgumentException {
        String selectQuery = "SELECT announcementId " +
                "FROM announcements NATURAL JOIN user_to_course " +
                "WHERE courseId IN (SELECT courseId FROM user_to_course WHERE userid = :userId) " +
                "ORDER BY date DESC";
        String mappingQuery = "SELECT a FROM Announcement a WHERE a.announcementId IN (:ids) ORDER BY a.date DESC";
        Map<String, Object> properties = new HashMap<>();
        properties.put("userId", userId);
        return listBy(properties, selectQuery, mappingQuery, pageRequest, Announcement.class);
    }

    @Transactional(readOnly = true)
    @Override
    public CampusPage<Announcement> listByCourse(Long courseId, CampusPageRequest pageRequest)
            throws PaginationArgumentException {
        String selectQuery = "SELECT announcementId " +
                "FROM announcements NATURAL JOIN user_to_course " +
                "WHERE courseId = :courseId " +
                "ORDER BY date DESC";
        String mappingQuery = "SELECT a FROM Announcement a WHERE a.announcementId IN (:ids) ORDER BY a.date DESC";
        Map<String, Object> properties = new HashMap<>();
        properties.put("courseId", courseId);
        return listBy(properties, selectQuery, mappingQuery, pageRequest, Announcement.class);
    }
}
