package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

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
    public Optional<Announcement> getById(Long id) {
        return announcementDao.getById(id);
    }

    @Override
    public CampusPage<Announcement> listByUser(Long userId, CampusPageRequest pageRequest) {
        return announcementDao.listByUser(userId, pageRequest);
    }

    @Override
    public CampusPage<Announcement> listByCourse(Long courseId, CampusPageRequest pageRequest) {
        return announcementDao.listByCourse(courseId, pageRequest);
    }

}
