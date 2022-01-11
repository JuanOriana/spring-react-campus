package ar.edu.itba.paw.webapp.security.service;

import ar.edu.itba.paw.webapp.security.api.model.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.security.api.model.Authority;

import java.util.Set;

public interface AuthenticationTokenService {
    String issueToken(String username, Set<Authority> authorities);
    AuthenticationTokenDetails parseToken(String token);
    String refreshToken(AuthenticationTokenDetails currentAuthenticationTokenDetails);
}
