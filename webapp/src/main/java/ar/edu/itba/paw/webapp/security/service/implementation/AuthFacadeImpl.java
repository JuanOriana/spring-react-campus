package ar.edu.itba.paw.webapp.security.service.implementation;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.security.api.basic.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {

    @Autowired
    private UserService userService;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!isAuthenticationBasic()) {
            return ((CampusUser)(authentication.getPrincipal())).toUser();
        }
        String username = (String) authentication.getPrincipal();
        return userService.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    private boolean isAuthenticationBasic() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof BasicAuthenticationToken;
    }

    public boolean isAdminUser() {
        return getCurrentUser().isAdmin();
    }
}
