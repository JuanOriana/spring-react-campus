package ar.edu.itba.paw.webapp.auth.filters;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.basic.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.auth.jwt.JwtAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Integer JWT_PREFIX_LENGTH = 7;
    private static final Integer BASIC_PREFIX_LENGTH = 6;


    public AuthenticationFilter() {
        super("/api/**");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String header = httpServletRequest.getHeader("Authorization");
        header = header != null ? header : "";
        if(header.startsWith("Bearer ")) {
            String jwtToken = header.substring(JWT_PREFIX_LENGTH);
            return getAuthenticationManager().authenticate(new JwtAuthenticationToken(jwtToken));
        } else if(header.startsWith("Basic ")) {
            String basicToken = header.substring(BASIC_PREFIX_LENGTH);
            return getAuthenticationManager().authenticate(new BasicAuthenticationToken(basicToken));
        } else {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ANONYMOUS"));
            return new AnonymousAuthenticationToken("anon", new User(), authorities);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
