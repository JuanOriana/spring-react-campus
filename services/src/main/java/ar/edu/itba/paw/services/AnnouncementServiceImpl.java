package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final int MIN_PAGE_COUNT = 1;

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public Announcement create(String title, String content, User author, Course course) {
        return announcementDao.create(LocalDateTime.now(), title, content, author, course);
    }

    @Override
    public boolean update(Long id, Announcement announcement) {
        return announcementDao.update(id, announcement);
    }

    @Override
    public boolean delete(Long id) {
        return announcementDao.delete(id);
    }

    @Override
    public List<Announcement> list(Long userId, Integer page, Integer pageSize) {
        return announcementDao.list(userId, page, pageSize);
    }

    @Override
    public int getPageCount(Long userId, Integer pageSize) {
        if(pageSize < MIN_PAGE_COUNT) return 0;
        return announcementDao.getPageCount(userId, pageSize);
    }

    @Override
    public List<Announcement> list(Long userId, Integer page, Integer pageSize, Comparator<Announcement> comparator) {
        return list(userId, page, pageSize).stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<Announcement> listByCourse(Long courseId) {
        return announcementDao.listByCourse(courseId);
    }

    @Override
    public List<Announcement> listByCourse(Long courseId, Comparator<Announcement> comparator) {
        return listByCourse(courseId).stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public Optional<Announcement> getById(Long id) {
        return announcementDao.getById(id);
    }
}
