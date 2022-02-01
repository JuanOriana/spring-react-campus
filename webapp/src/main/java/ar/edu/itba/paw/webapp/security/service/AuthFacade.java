package ar.edu.itba.paw.webapp.security.service;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface AuthFacade {
    User getCurrentUser();
    Long getCurrentUserId();
    boolean isAdminUser();
}
