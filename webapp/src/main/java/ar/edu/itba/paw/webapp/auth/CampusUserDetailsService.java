package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
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

    @Autowired
    private PasswordEncoder encoder;

    private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[.\0-9A-Za-z]{53}");

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        final Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.isAdmin() ? "ADMIN" : "USER"));
        final String password;
        if(user.getPassword() == null || !BCRYPT_PATTERN.matcher(user.getPassword()).matches()) {
            // TO-DO: Add method to update password in db to be hashed
            password = encoder.encode(user.getPassword());
        } else {
            password = user.getPassword();
        }
        return new CampusUser(username, password, authorities, user.getFileNumber(), user.getUserId(), user.getName(),
                user.getSurname(), user.getEmail(), user.isAdmin());
    }
}
