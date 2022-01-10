package ar.edu.itba.paw.webapp.auth.basic;

import ar.edu.itba.paw.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BasicAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String value;

    public BasicAuthenticationToken(String basicAuthorization){
        super(new User(),new ArrayList<>());
        this.value = basicAuthorization;
    }
    public BasicAuthenticationToken(String username, String password){
        super(username,password);
    }
    public BasicAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
    public BasicAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BasicAuthenticationToken that = (BasicAuthenticationToken) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
