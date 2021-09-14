package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.core.Authentication;

public interface AuthFacade {
    CampusUser getCurrentUser();
}
