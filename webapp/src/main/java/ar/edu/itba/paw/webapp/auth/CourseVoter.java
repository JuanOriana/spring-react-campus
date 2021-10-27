package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CourseVoter implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private FileService fileService;

    static final Pattern GET_FILE_PATTERN = Pattern.compile("/files/(\\d+)");
    static final Pattern UPLOAD_FILE_PATTERN = Pattern.compile("/course/(\\d+)/files");
    static final Pattern UPLOAD_ANNOUNCEMENT_PATTERN = Pattern.compile("/course/(\\d+)/announcements");
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
        Matcher getFileMatcher = GET_FILE_PATTERN.matcher(url);
        Matcher uploadFileMatcher = UPLOAD_FILE_PATTERN.matcher(url);
        Matcher uploadAnnouncementMatcher = UPLOAD_ANNOUNCEMENT_PATTERN.matcher(url);
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
