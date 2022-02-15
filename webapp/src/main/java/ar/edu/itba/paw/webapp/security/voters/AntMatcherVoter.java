package ar.edu.itba.paw.webapp.security.voters;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.*;
import ar.edu.itba.paw.webapp.security.api.models.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.models.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    @Autowired
    private ExamService examService;

    private Long getUserId(Authentication authentication) {
        return getUser(authentication).getUserId();
    }

    private boolean isAdmin(Authentication authentication) {
        return getUser(authentication).isAdmin();
    }

    private User getUser(Authentication authentication) {
        if(authentication instanceof BasicAuthenticationToken) {
            return userService.findByUsername(((BasicAuthenticationToken)authentication).getPrincipal()).orElseThrow(UserNotFoundException::new);
        }
        return ((CampusUser)(authentication.getPrincipal())).toUser();
    }

    public boolean canAccessAnnouncementById(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        if(isAdmin(authentication)) return true;
        Announcement announcement = announcementService.findById(id).orElseThrow(AnnouncementNotFoundException::new);
        return courseService.belongs(getUserId(authentication), announcement.getCourse().getCourseId());
    }

    public boolean canDeleteAnnouncementById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Announcement announcement = announcementService.findById(id).orElseThrow(AnnouncementNotFoundException::new);
        return courseService.isPrivileged(getUserId(authentication), announcement.getCourse().getCourseId());
    }

    public boolean canAccessAnswerById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Answer answer = answerService.findById(id).orElseThrow(AnswerNotFoundException::new);
        Long userId = getUserId(authentication);
        return answer.getStudent().getUserId().equals(userId) || answer.getTeacher().getUserId().equals(userId);
    }

    public boolean canDeleteAnswerById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Answer answer = answerService.findById(id).orElseThrow(AnswerNotFoundException::new);
        Long courseId = answer.getExam().getCourse().getCourseId();
        Long userId = getUserId(authentication);
        return courseService.isPrivileged(userId, courseId);
    }

    public boolean isPrivilegedInCourse(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        if(isAdmin(authentication)) return true;
        Course course = courseService.findById(id).orElseThrow(CourseNotFoundException::new);
        Long userId = getUserId(authentication);
        return courseService.isPrivileged(userId, course.getCourseId());
    }

    public boolean isStudentInCourse(Authentication authentication, Long id) {
        return !isPrivilegedInCourse(authentication, id);
    }

    public boolean canPostAnnouncementByCourseId(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Course course = courseService.findById(id).orElseThrow(CourseNotFoundException::new);
        return courseService.isPrivileged(getUserId(authentication), course.getCourseId());
    }

    public boolean canAccessCourseById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Course course = courseService.findById(id).orElseThrow(CourseNotFoundException::new);
        return courseService.belongs(getUserId(authentication), course.getCourseId());
    }

    public boolean canAccessUserById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        User user = userService.findById(id).orElseThrow(UserNotFoundException::new);
        return getUserId(authentication).equals(user.getUserId());
    }

    public boolean canAccessFileById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        FileModel fileModel = fileService.findById(id).orElseThrow(FileNotFoundException::new);
        return courseService.belongs(getUserId(authentication), fileModel.getCourse().getCourseId());
    }

    public boolean canDeleteFileById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        FileModel fileModel = fileService.findById(id).orElseThrow(FileNotFoundException::new);
        return courseService.isPrivileged(getUserId(authentication), fileModel.getCourse().getCourseId());
    }

    public boolean canAccessExamById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Exam exam = examService.findById(id).orElseThrow(ExamNotFoundException::new);
        Long examId = exam.getExamId();
        Long courseId = exam.getCourse().getCourseId();
        Long userId = getUserId(authentication);
        boolean isPrivileged = courseService.isPrivileged(userId, courseId);
        if(!courseService.belongs(userId, courseId)) return false;
        if(!examService.belongs(examId, courseId)) return false;
        if(!isPrivileged && answerService.didUserDeliver(examId, userId)) return false;
        if(isPrivileged) return true;
        return !LocalDateTime.now().isBefore(exam.getStartTime())
                && !LocalDateTime.now().isAfter(exam.getEndTime());
    }
    public boolean canDeleteExamById(Authentication authentication, Long id) {
        if(isAdmin(authentication)) return true;
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Exam exam = examService.findById(id).orElseThrow(ExamNotFoundException::new);
        return courseService.isPrivileged(getUserId(authentication), exam.getCourse().getCourseId());
    }
}
