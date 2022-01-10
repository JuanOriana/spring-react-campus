package ar.edu.itba.paw.webapp.auth.jwt;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import ar.edu.itba.paw.webapp.auth.exceptions.JwtTokenMalformedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // Un-used
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        Optional<User> oUser = JwtManager.parseToken(jwtAuthenticationToken.getToken());
        if(!oUser.isPresent()) throw new JwtTokenMalformedException("JWT token is invalid");
        User user = oUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        if(Boolean.TRUE.equals(user.isAdmin())) authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return new CampusUser(user.getUsername(), user.getPassword(), authorities, user.getFileNumber(), user.getUserId(), user.getName(),
                user.getSurname(), user.getEmail(), user.isAdmin(), user.getImage());
    }
}
