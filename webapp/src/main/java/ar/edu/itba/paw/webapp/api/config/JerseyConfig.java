package ar.edu.itba.paw.webapp.api.config;

import ar.edu.itba.paw.webapp.api.provider.ObjectMapperProvider;
import ar.edu.itba.paw.webapp.mappers.ApiExceptionMapper;
import ar.edu.itba.paw.webapp.mappers.ServerErrorMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(ObjectMapperProvider.class);

    }
}
