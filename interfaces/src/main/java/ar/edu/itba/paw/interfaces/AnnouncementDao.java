package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Announcement;

import java.util.List;

public interface AnnouncementDao {
    boolean create(Announcement announcement);
    boolean update(int id, Announcement announcement);
    boolean delete(int id);
    List<Announcement> list();
    List<Announcement> listByCourse(int courseId);
    Announcement getById(int id);
}
