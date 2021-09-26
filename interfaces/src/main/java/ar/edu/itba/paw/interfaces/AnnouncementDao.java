package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {
    Announcement create(LocalDateTime date, String title, String content, User author, Course course);
    boolean update(Long id, Announcement announcement);
    boolean delete(Long id);
    int getPageCount(Long userId, Integer pageSize);
    List<Announcement> listByCourse(Long courseId);
    Optional<Announcement> getById(Long id);
    Page<Announcement> findAnnouncementByPage(Long userId, Pageable pageable);
}
