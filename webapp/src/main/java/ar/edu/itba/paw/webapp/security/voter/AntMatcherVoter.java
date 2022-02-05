package ar.edu.itba.paw.webapp.security.voter;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.*;
import ar.edu.itba.paw.webapp.security.api.basic.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AntMatcherVoter {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    private Long getUserId(Authentication authentication) {
        if(authentication instanceof BasicAuthenticationToken) {
            User user = userService.findByUsername(((BasicAuthenticationToken)authentication).getPrincipal()).orElseThrow(UserNotFoundException::new);
            return user.getUserId();
        }
        return ((CampusUser)(authentication.getPrincipal())).toUser().getUserId();
    }

    public boolean canAccessAnnouncementById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Announcement announcement = announcementService.findById(id).orElseThrow(AnnouncementNotFoundException::new);
        return courseService.belongs(getUserId(authentication), announcement.getCourse().getCourseId());
    }

    public boolean canDeleteAnnouncementById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Announcement announcement = announcementService.findById(id).orElseThrow(AnnouncementNotFoundException::new);
        return courseService.isPrivileged(getUserId(authentication), announcement.getCourse().getCourseId());
    }

    // TODO: Add time and delivery checks
    public boolean canAccessAnswerById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Answer answer = answerService.findById(id).orElseThrow(AnswerNotFoundException::new);
        Long userId = getUserId(authentication);
        return answer.getStudent().getUserId().equals(userId);
    }

    public boolean canDeleteAnswerById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Answer answer = answerService.findById(id).orElseThrow(AnswerNotFoundException::new);
        Long courseId = answer.getExam().getCourse().getCourseId();
        Long userId = getUserId(authentication);
        return courseService.isPrivileged(userId, courseId);
    }

    public boolean isPrivilegedInCourse(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Course course = courseService.findById(id).orElseThrow(CourseNotFoundException::new);
        Long userId = getUserId(authentication);
        return courseService.isPrivileged(userId, course.getCourseId());
    }

    public boolean canPostAnnouncementByCourseId(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Course course = courseService.findById(id).orElseThrow(CourseNotFoundException::new);
        return courseService.isPrivileged(getUserId(authentication), course.getCourseId());
    }

    public boolean canAccessCourseById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Course course = courseService.findById(id).orElseThrow(CourseNotFoundException::new);
        return courseService.belongs(getUserId(authentication), course.getCourseId());
    }

    public boolean canAccessUserById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        User user = userService.findById(id).orElseThrow(UserNotFoundException::new);
        return getUserId(authentication).equals(user.getUserId());
    }

    public boolean canAccessFileById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        FileModel fileModel = fileService.findById(id).orElseThrow(FileNotFoundException::new);
        return courseService.belongs(getUserId(authentication), fileModel.getCourse().getCourseId());
    }

    public boolean canDeleteFileById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        FileModel fileModel = fileService.findById(id).orElseThrow(FileNotFoundException::new);
        return courseService.isPrivileged(getUserId(authentication), fileModel.getCourse().getCourseId());
    }
}
