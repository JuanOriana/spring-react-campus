package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;

public interface AuthFacade {
    User getCurrentUser();
    Long getCurrentUserId();
}
