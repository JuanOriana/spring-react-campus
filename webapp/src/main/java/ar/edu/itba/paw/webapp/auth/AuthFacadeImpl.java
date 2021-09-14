package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {
    @Override
    public CampusUser getCurrentUser() {
        return (CampusUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
