package ar.edu.itba.paw.webapp.security.voter;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exception.AnnouncementNotFoundException;
import ar.edu.itba.paw.models.exception.AnswerNotFoundException;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
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

    public boolean canAccessAnnouncement(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Announcement announcement = announcementService.findById(id).orElseThrow(AnnouncementNotFoundException::new);
        return courseService.belongs(getUserId(authentication), announcement.getCourse().getCourseId());
    }

    public boolean hasCoursePrivileges(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Announcement announcement = announcementService.findById(id).orElseThrow(AnnouncementNotFoundException::new);
        return courseService.isPrivileged(getUserId(authentication), announcement.getCourse().getCourseId());
    }

    public boolean canAccessAnswer(Authentication authentication, Long id) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Answer answer = answerService.findById(id).orElseThrow(AnswerNotFoundException::new);
        Long userId = getUserId(authentication);
        return answer.getStudent().getUserId().equals(userId);
    }
}
