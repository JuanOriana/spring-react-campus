package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Repository
public class AnnouncementDaoJpa implements AnnouncementDao {

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


    private int getTotalPageCount(String query, String propertyName, Long propertyValue, Integer pageSize) {
        String rowCountSql = "SELECT count(1) AS row_count FROM (" + query + ") as foo";
        final Query announcementCountQuery = em.createNativeQuery(rowCountSql);
        announcementCountQuery.setParameter(propertyName, propertyValue);
        Number count = (Number) announcementCountQuery.getSingleResult();
        return (int) Math.ceil(count.doubleValue() / pageSize);
    }

    private CampusPage<Announcement> listBy(String selectQuery, String propertyName, Long propertyValue,
                                            CampusPageRequest pageRequest) {
        int pageCount = getTotalPageCount(selectQuery, propertyName, propertyValue, pageRequest.getPageSize());
        if(pageCount == 0) return new CampusPage<>();
        if(pageCount > pageRequest.getPageSize()) throw new PaginationArgumentException();
        String announcementIdsQueryString = selectQuery + " LIMIT " + pageRequest.getPageSize() + " OFFSET "
                + (pageRequest.getPage() - 1) * pageRequest.getPageSize();
        Query announcementIdsQuery = em.createNativeQuery(announcementIdsQueryString);
        announcementIdsQuery.setParameter(propertyName, propertyValue);
        List<Integer> announcementIds = announcementIdsQuery.getResultList();
        TypedQuery<Announcement> announcementQuery = em.createQuery("SELECT a FROM Announcement a WHERE a.announcementId IN (:ids) ORDER BY a.date DESC",
                Announcement.class);
        announcementQuery.setParameter("ids", announcementIds.stream()
                                                 .mapToLong(Integer::longValue)
                                                 .boxed().collect(Collectors.toList()));
        return new CampusPage<>(announcementQuery.getResultList(), pageRequest.getPageSize(), pageRequest.getPage(), pageCount);
    }

    @Transactional(readOnly = true)
    @Override
    public CampusPage<Announcement> listByUser(Long userId, CampusPageRequest pageRequest)
            throws PaginationArgumentException {
        String selectQuery = "SELECT announcementId " +
                "FROM announcements NATURAL JOIN user_to_course " +
                "WHERE courseId IN (SELECT courseId FROM user_to_course WHERE userid = :userId) " +
                "ORDER BY date DESC";
        return listBy(selectQuery, "userId", userId, pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public CampusPage<Announcement> listByCourse(Long courseId, CampusPageRequest pageRequest)
            throws PaginationArgumentException {
        String selectQuery = "SELECT announcementId " +
                "FROM announcements NATURAL JOIN user_to_course " +
                "WHERE courseId = :courseId " +
                "ORDER BY date DESC";
        return listBy(selectQuery, "courseId", courseId, pageRequest);
    }
}
