package ar.edu.itba.paw.webapp.auth.basic;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import ar.edu.itba.paw.webapp.auth.exceptions.InvalidCredentialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BasicAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> authentication) {
        return BasicAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // Un-used
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        BasicAuthenticationToken basicAuthenticationToken = (BasicAuthenticationToken) authentication;
        String[] credentials = new String(Base64.getDecoder().decode(basicAuthenticationToken.getValue())).split(":");
        Optional<User> oUser;
        if(credentials.length != 2) {
            throw new InvalidCredentialException("Invalid username/password");
        }
        oUser = userService.findByUsername(credentials[0]);
        if(!oUser.isPresent() || !passwordEncoder.matches(credentials[1], oUser.get().getPassword())) {
            throw new InvalidCredentialException("Invalid username/password");
        }
        User user = oUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        if(Boolean.TRUE.equals(user.isAdmin())) authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return new CampusUser(credentials[0], credentials[1], authorities, user.getFileNumber(), user.getUserId(), user.getName(),
                user.getSurname(), user.getEmail(), user.isAdmin(), user.getImage());
    }
}
