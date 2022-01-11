package ar.edu.itba.paw.webapp.api.config;

import ar.edu.itba.paw.webapp.controller.TokenController;
import ar.edu.itba.paw.webapp.controller.UserController;
import ar.edu.itba.paw.webapp.security.api.exceptionmapper.AccessDeniedExceptionMapper;
import ar.edu.itba.paw.webapp.security.api.exceptionmapper.AuthenticationExceptionMapper;
import ar.edu.itba.paw.webapp.security.api.exceptionmapper.NotAllowedExceptionMapper;
import ar.edu.itba.paw.webapp.security.api.exceptionmapper.NotFoundExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(AccessDeniedExceptionMapper.class);
        register(AuthenticationExceptionMapper.class);
        register(NotAllowedExceptionMapper.class);
        register(NotFoundExceptionMapper.class);
        register(UserController.class);
        register(TokenController.class);
    }
}
