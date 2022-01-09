package ar.edu.itba.paw.webapp.security.api.jwt;

import ar.edu.itba.paw.webapp.security.api.model.PayloadAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends PayloadAuthenticationFilter {


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, String payload) {
        super(authenticationManager, authenticationEntryPoint, payload);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            Authentication authenticationRequest = new JwtAuthenticationToken(this.getPayload());
            Authentication authenticationResult = this.getAuthenticationManager().authenticate(authenticationRequest);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationResult);
            SecurityContextHolder.setContext(context);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            this.getAuthenticationEntryPoint().commence(request, response, e);
            return;
        }

        this.doFilter(request, response, chain);
    }
}
