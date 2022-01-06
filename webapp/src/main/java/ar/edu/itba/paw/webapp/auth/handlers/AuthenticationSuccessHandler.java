package ar.edu.itba.paw.webapp.auth.handlers;

import ar.edu.itba.paw.webapp.auth.jwt.JwtManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        boolean isAdmin = false;
        boolean isUser = false;
        for(GrantedAuthority a : authentication.getAuthorities()) {
            if(a.getAuthority().equals("ADMIN")){
                isAdmin = true;
            }
            if(a.getAuthority().equals("USER")){
                isUser = true;
            }
        }
        boolean hasBasicHeader = request.getHeader("Authorization").contains("Basic");
        if(isUser && hasBasicHeader){
            response.addHeader("Authorization","Bearer " + JwtManager.tokenize(authentication.getName(), isAdmin));
        }
    }
}
