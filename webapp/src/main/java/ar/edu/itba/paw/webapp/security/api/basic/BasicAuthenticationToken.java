package ar.edu.itba.paw.webapp.security.api.basic;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BasicAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public BasicAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public BasicAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public BasicAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return (String)super.getPrincipal();
    }

    @Override
    public String getCredentials() {
        return (String)super.getCredentials();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }
}
