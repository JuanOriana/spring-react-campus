package ar.edu.itba.paw.webapp.api.config;

import ar.edu.itba.paw.webapp.security.api.exceptionmapper.AccessDeniedExceptionMapper;
import ar.edu.itba.paw.webapp.security.api.exceptionmapper.AuthenticationExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(AccessDeniedExceptionMapper.class);
        register(AuthenticationExceptionMapper.class);
    }
}
