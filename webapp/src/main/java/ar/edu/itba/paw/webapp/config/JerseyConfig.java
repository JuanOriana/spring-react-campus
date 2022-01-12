package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.mappers.ApiExceptionMapper;
import ar.edu.itba.paw.webapp.mappers.ServerErrorMapper;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(ApiExceptionMapper.class);
        register(ServerErrorMapper.class);
        register(MultiPartFeature.class);
    }
}
