package ar.edu.itba.paw.webapp.security.service.implementation;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.security.api.jwt.JwtAuthenticationToken;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.ForbiddenException;
import java.util.Optional;

@Component
public class AuthFacadeImpl implements AuthFacade {

    @Autowired
    private UserService userService;

    @Override
    public User getCurrentUser() {
        return ((CampusUser)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).toUser();
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof JwtAuthenticationToken) {
            return ((CampusUser)(authentication.getPrincipal())).getUserId();
        }
        String username = (String) authentication.getPrincipal();
        return userService.findByUsername(username).orElseThrow(ForbiddenException::new).getUserId();
    }

    public boolean isAdminUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CampusUser) {
            return ((CampusUser)principal).isAdmin();
        } else {
            return false;
        }
    }
}
