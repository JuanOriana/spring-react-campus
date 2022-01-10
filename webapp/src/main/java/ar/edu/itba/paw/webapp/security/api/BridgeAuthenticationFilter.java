package ar.edu.itba.paw.webapp.security.api;

import ar.edu.itba.paw.webapp.security.api.basic.BasicAuthenticationFilter;
import ar.edu.itba.paw.webapp.security.api.jwt.JwtAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

public class BridgeAuthenticationFilter extends OncePerRequestFilter {
    private final int BASIC_TOKEN_OFFSET = 6;
    private final int JWT_TOKEN_OFFSET = 7;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public BridgeAuthenticationFilter(AuthenticationManager authenticationManager,
                                        AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        boolean isPresent = authorizationHeader != null;
        if(isPresent) {
            if (authorizationHeader.startsWith("Bearer ")) {
                String payload = authorizationHeader.substring(JWT_TOKEN_OFFSET);
                BasicAuthenticationFilter basicAuthenticationFilter = new BasicAuthenticationFilter(authenticationManager, authenticationEntryPoint, payload);
                basicAuthenticationFilter.doFilter(request, response, chain);
            } else if (authorizationHeader.startsWith("Basic ")) {
                String payload = authorizationHeader.substring(BASIC_TOKEN_OFFSET);
                JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, authenticationEntryPoint, payload);
                jwtAuthenticationFilter.doFilter(request, response, chain);
            }
        }

        chain.doFilter(request, response);

    }
}
