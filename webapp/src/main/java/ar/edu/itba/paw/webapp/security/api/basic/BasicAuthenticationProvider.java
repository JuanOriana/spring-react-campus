package ar.edu.itba.paw.webapp.security.api.basic;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.security.api.exception.InvalidUsernamePasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BasicAuthenticationToken auth = (BasicAuthenticationToken) authentication;
        String[] credentials = new String(Base64.getDecoder().decode(auth.getToken())).split(":");
        if(credentials.length != 2) {
            throw new InvalidUsernamePasswordException("Invalid username/password");
        }
        Optional<User> maybeUser = userService.findByUsername(credentials[0]);
        if(!maybeUser.isPresent() || !passwordEncoder.matches(credentials[1], maybeUser.get().getPassword())){
            throw new InvalidUsernamePasswordException("Invalid username/password");
        }

        User user = maybeUser.get();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER"));
        if(user.isAdmin()){
            authorityList.add(new SimpleGrantedAuthority("ADMIN"));
        }
        BasicAuthenticationToken authenticationToken = new BasicAuthenticationToken(credentials[0], credentials[1], authorityList);
        authenticationToken.setToken(auth.getToken());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (BasicAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
