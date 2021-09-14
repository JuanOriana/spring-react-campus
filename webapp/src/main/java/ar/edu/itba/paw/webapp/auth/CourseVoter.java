package ar.edu.itba.paw.webapp.auth;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Permissions;
import ar.edu.itba.paw.models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class CourseVoter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseVoter.class);

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    public boolean checkUserCourseAccess(Authentication authentication, Integer courseId) {
        return courseService.belongs(((CampusUser)authentication.getPrincipal()).getUserId(), courseId);
    }

    public boolean checkUserCourseRole(Authentication authentication, Integer courseId) {
        Optional<Role> currentUserRole = userService.getRole(((CampusUser)authentication.getPrincipal()).getUserId(),
                courseId);
        return currentUserRole.isPresent() && currentUserRole.get().getRoleId() >= Permissions.HELPER.getValue();
    }
}
