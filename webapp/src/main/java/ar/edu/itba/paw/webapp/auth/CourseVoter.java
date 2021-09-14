package ar.edu.itba.paw.webapp.auth;
import ar.edu.itba.paw.interfaces.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

public class CourseVoter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseVoter.class);

    @Autowired
    CourseService courseService;

    public boolean checkUserCourseAccess(Authentication authentication, int courseId) {
        return courseService.belongs(((CampusUser)authentication.getPrincipal()).getUserId(), courseId);
    }
}
