package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.MailingService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MailingService mailingService;


    @Transactional
    @Override
    public Announcement create(String title, String content, User author, Course course) {
        Announcement announcement = announcementDao.create(LocalDateTime.now(), title, content, author, course);
        List<User> userList = courseService.getStudents(announcement.getCourse().getCourseId());
        List<String> emailList = new ArrayList<>();
        userList.forEach(u->emailList.add(u.getEmail()));
        mailingService.sendNewAnnouncementNotification(emailList, title, content, course);
        return announcement;
    }

    @Transactional
    @Override
    public boolean update(Long id, Announcement announcement) {
        return announcementDao.update(id, announcement);
    }

    @Transactional
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
