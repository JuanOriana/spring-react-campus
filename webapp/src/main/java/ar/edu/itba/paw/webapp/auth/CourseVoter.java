package ar.edu.itba.paw.webapp.auth;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.FileDao;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class CourseVoter {

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private FileService fileService;

    public boolean hasCourseAccess(Authentication authentication, Long courseId) {
        boolean isAnonymous = authentication instanceof AnonymousAuthenticationToken;
        User user = authFacade.getCurrentUser();
        return !user.isAdmin() && !isAnonymous && courseService.belongs(user.getUserId(), courseId);
    }

    public boolean hasCoursePrivileges(Authentication authentication, Long courseId) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        User user = authFacade.getCurrentUser();
        return !user.isAdmin() && courseService.isPrivileged(user.getUserId(), courseId);
    }

    public boolean hasFileAccess(Authentication authentication, Long fileId) {
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        User user = authFacade.getCurrentUser();
        return !user.isAdmin() && fileService.hasAccess(fileId, user.getUserId());
    }
}
