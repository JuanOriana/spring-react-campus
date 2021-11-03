package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CampusVoter implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private FileService fileService;

    @Autowired
    private ExamService examService;

    @Autowired
    private AnswerService answerService;

    static final Pattern GET_FILE_PATTERN = Pattern.compile("/file/(\\d+)");
    static final Pattern UPLOAD_FILE_PATTERN = Pattern.compile("/course/(\\d+)/files");
    static final Pattern UPLOAD_ANNOUNCEMENT_PATTERN = Pattern.compile("/course/(\\d+)/announcements");
    static final Pattern GET_COURSE_EXAM_PATTERN = Pattern.compile("/course/(\\d+)/exam/(\\d+)");
    static final Pattern GET_COURSE_PATTERN = Pattern.compile("/course/(\\d+)");

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(FilterInvocation.class);
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {
        final String url = fi.getRequestUrl();
        final String method = fi.getHttpRequest().getMethod();
        Matcher getCourseMatcher = GET_COURSE_PATTERN.matcher(url);
        Matcher getCourseExamMatcher = GET_COURSE_EXAM_PATTERN.matcher(url);
        Matcher getFileMatcher = GET_FILE_PATTERN.matcher(url);
        Matcher uploadFileMatcher = UPLOAD_FILE_PATTERN.matcher(url);
        Matcher uploadAnnouncementMatcher = UPLOAD_ANNOUNCEMENT_PATTERN.matcher(url);
        if(getCourseExamMatcher.find()) return voteExamAccess(authentication, getCourseExamMatcher);
        if(getFileMatcher.find()) return voteFileAccess(authentication, getMappingValue(getFileMatcher));
        if(method.equals("POST") && uploadAnnouncementMatcher.find()) return voteCoursePrivileges(authentication, getMappingValue(uploadAnnouncementMatcher));
        if(method.equals("POST") && uploadFileMatcher.find()) return voteCoursePrivileges(authentication, getMappingValue(uploadFileMatcher));
        if(getCourseMatcher.find()) return voteCourseAccess(authentication, getMappingValue(getCourseMatcher));
        return ACCESS_ABSTAIN;
    }



    private Long getMappingValue(Matcher m) {
        return Long.valueOf(m.group(1));
    }

    private boolean isAdminOrAnonymous(Authentication authentication) {
        if(authentication instanceof AnonymousAuthenticationToken) return true;
        User user = authFacade.getCurrentUser();
        return user.isAdmin();
    }

    private int voteExamAccess(Authentication authentication, Matcher getCourseExamMatcher) {
        Long courseId = Long.valueOf(getCourseExamMatcher.group(1));
        Long examId = Long.valueOf(getCourseExamMatcher.group(2));
        boolean isPrivileged = courseService.isPrivileged(authFacade.getCurrentUserId(), courseId);
        if(isAdminOrAnonymous(authentication)) return ACCESS_DENIED;
        if(!courseService.belongs(authFacade.getCurrentUserId(), courseId)) return ACCESS_DENIED;
        if(!examService.belongs(examId, courseId)) return ACCESS_DENIED;
        if(!isPrivileged && answerService.didUserDeliver(examId, authFacade.getCurrentUserId())) return ACCESS_DENIED;
        if(isPrivileged) return ACCESS_GRANTED;
        Optional<Exam> exam = examService.findById(examId);
        if(!exam.isPresent() || LocalDateTime.now().isBefore(exam.get().getStartTime())
           || LocalDateTime.now().isAfter(exam.get().getEndTime())) return ACCESS_DENIED;
        return ACCESS_GRANTED;
    }

    private int voteFileAccess(Authentication authentication, Long fileId) {
        if(isAdminOrAnonymous(authentication)) return ACCESS_DENIED;
        return fileService.hasAccess(fileId, authFacade.getCurrentUserId()) ? ACCESS_GRANTED : ACCESS_DENIED;
    }

    private int voteCourseAccess(Authentication authentication, Long courseId) {
        if(isAdminOrAnonymous(authentication)) return ACCESS_DENIED;
        return courseService.belongs(authFacade.getCurrentUserId(), courseId) ? ACCESS_GRANTED : ACCESS_DENIED;
    }

    private int voteCoursePrivileges(Authentication authentication, Long courseId) {
        if(isAdminOrAnonymous(authentication)) return ACCESS_DENIED;
        return courseService.isPrivileged(authFacade.getCurrentUserId(), courseId) ? ACCESS_GRANTED : ACCESS_DENIED;
    }
}
