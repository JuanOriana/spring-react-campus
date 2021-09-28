package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AnnouncementDao {
    Announcement create(LocalDateTime date, String title, String content, User author, Course course);
    boolean update(Long id, Announcement announcement);
    boolean delete(Long id);
    Optional<Announcement> getById(Long id);
    CampusPage<Announcement> listByUser(Long userId, CampusPageRequest pageRequest) throws PaginationArgumentException;
    CampusPage<Announcement> listByCourse(Long courseId, CampusPageRequest pageRequest) throws PaginationArgumentException;
}
