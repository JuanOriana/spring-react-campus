package ar.edu.itba.paw.webapp.security.api.model;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

public abstract class PayloadAuthenticationFilter extends OncePerRequestFilter {
    private final String payload;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public PayloadAuthenticationFilter(AuthenticationManager authenticationManager,
                                       AuthenticationEntryPoint authenticationEntryPoint,
                                       String payload) {
        this.payload = payload;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationManager = authenticationManager;
    }

    public String getPayload() {
        return payload;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }
}
