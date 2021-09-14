package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {
    Announcement create(Announcement announcement);
    boolean update(Integer id, Announcement announcement);
    boolean delete(Integer id);
    int getPageCount(Integer pageSize);
    List<Announcement> list(Integer userId, Integer page, Integer pageSize);
    List<Announcement> listByCourse(Integer courseId);
    Optional<Announcement> getById(Integer id);
}
