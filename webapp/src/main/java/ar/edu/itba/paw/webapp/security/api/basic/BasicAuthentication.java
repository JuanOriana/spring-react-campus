package ar.edu.itba.paw.webapp.security.api.basic;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BasicAuthentication extends UsernamePasswordAuthenticationToken {

    private String token;

    public BasicAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public BasicAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public BasicAuthentication(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
