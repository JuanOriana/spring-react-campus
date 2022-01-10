package ar.edu.itba.paw.webapp.security.api;

import ar.edu.itba.paw.webapp.security.api.basic.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.api.model.Authority;
import ar.edu.itba.paw.webapp.security.service.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private AuthenticationTokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication instanceof BasicAuthenticationToken) {
            BasicAuthenticationToken basicAuthenticationToken = (BasicAuthenticationToken)authentication;
            Set<Authority> authorities = basicAuthenticationToken.getAuthorities().stream()
                    .map(grantedAuthority -> Authority.valueOf(grantedAuthority.toString()))
                    .collect(Collectors.toSet());
            response.addHeader("Authorization", "Bearer " + tokenService
                    .issueToken(basicAuthenticationToken.getPrincipal(), authorities));
        }
    }
}
