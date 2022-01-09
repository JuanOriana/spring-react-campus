package ar.edu.itba.paw.webapp.security.service;

import ar.edu.itba.paw.models.User;

public interface AuthFacade {
    User getCurrentUser();
    Long getCurrentUserId();
}
