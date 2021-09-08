package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {
    Announcement create(Announcement announcement);
    boolean update(long id, Announcement announcement);
    boolean delete(long id);
    List<Announcement> list(long page, long pageSize);
    List<Announcement> listByCourse(long courseId);
    Optional<Announcement> getById(long id);
}
