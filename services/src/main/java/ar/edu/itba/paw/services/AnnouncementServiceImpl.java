package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AnnouncementDao;
import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.MailingService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        mailingService.broadcastAnnouncementNotification(emailList, title, content, course, author, baseUrl);
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


    @Transactional(readOnly = true)
    @Override
    public Optional<Announcement> findById(Long id) {
        return announcementDao.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public CampusPage<Announcement> listByUser(Long userId, Integer page, Integer pageSize) {
        return announcementDao.listByUser(userId, new CampusPageRequest(page, pageSize));
    }

    @Transactional(readOnly = true)
    @Override
    public CampusPage<Announcement> listByCourse(Long courseId, Integer page, Integer pageSize) {
        return announcementDao.listByCourse(courseId, new CampusPageRequest(page, pageSize));
    }

}
