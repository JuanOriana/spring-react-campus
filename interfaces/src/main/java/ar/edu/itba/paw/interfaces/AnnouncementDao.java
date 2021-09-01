package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {
    boolean create(Announcement announcement);
    boolean update(int id, Announcement announcement);
    boolean delete(int id);
    List<Announcement> list();
    List<Announcement> listByCourse(int courseId);
    Optional<Announcement> getById(int id);
}
