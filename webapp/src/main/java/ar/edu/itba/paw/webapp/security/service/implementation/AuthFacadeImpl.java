package ar.edu.itba.paw.webapp.security.service.implementation;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {
    @Override
    public User getCurrentUser() {
        return ((CampusUser)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).toUser();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
}
