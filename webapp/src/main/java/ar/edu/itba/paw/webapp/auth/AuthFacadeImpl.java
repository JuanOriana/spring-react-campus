package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {
    @Override
    public User getCurrentUser() {
        return ((CampusUser)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).toUser();
    }
}
