package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Optional;

@Primary
@Repository
public class AnnouncementDaoJpa implements AnnouncementDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Announcement create(LocalDateTime date, String title, String content, User author, Course course) {
        return null;
    }

    @Override
    public boolean update(Long id, Announcement announcement) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional<Announcement> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CampusPage<Announcement> listByUser(Long userId, CampusPageRequest pageRequest) throws PaginationArgumentException {
        return null;
    }

    @Override
    public CampusPage<Announcement> listByCourse(Long courseId, CampusPageRequest pageRequest) throws PaginationArgumentException {
        return null;
    }
}
