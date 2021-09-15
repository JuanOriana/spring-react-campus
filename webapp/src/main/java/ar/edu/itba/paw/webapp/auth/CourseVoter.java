package ar.edu.itba.paw.webapp.auth;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class CourseVoter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseVoter.class);

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    FileDao fileDao;

    public boolean hasCourseAccess(Authentication authentication, Integer courseId) {
        boolean isAnonymous = authentication instanceof AnonymousAuthenticationToken;
        return !isAnonymous && courseService.belongs(((CampusUser)authentication.getPrincipal()).getUserId(), courseId);
    }

    public boolean hasCoursePrivileges(Authentication authentication, Integer courseId) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return courseService.isPrivileged(((CampusUser)authentication.getPrincipal()).getUserId(), courseId);
    }

    public boolean hasFileAccess(Authentication authentication, Integer fileId) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return fileDao.hasAccess(fileId, ((CampusUser)authentication.getPrincipal()).getUserId());
    }
}
