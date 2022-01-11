package ar.edu.itba.paw.webapp.security.service.implementation;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;

@Component
public class CampusUserDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;

    private final Pattern BCRYPT_HASH_PATTERN = Pattern.compile("^\\$2[ayb]\\$.{56}$");

    private final UserService userService;

    @Autowired
    public CampusUserDetailsService(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        final Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        if(Boolean.TRUE.equals(user.isAdmin())) authorities.add(new SimpleGrantedAuthority("ADMIN"));
        final String password;
        if(user.getPassword() == null || !BCRYPT_HASH_PATTERN.matcher(user.getPassword()).matches()) {
            password = encoder.encode(user.getPassword());
            user.setPassword(password);
            userService.update(user.getUserId(), user);
        } else {
            password = user.getPassword();
        }
        return new CampusUser(username, password, authorities, user.getFileNumber(), user.getUserId(), user.getName(),
                user.getSurname(), user.getEmail(), user.isAdmin(), user.getImage());
    }
}
