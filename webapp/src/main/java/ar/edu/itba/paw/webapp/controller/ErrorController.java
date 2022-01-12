package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Component;
import javax.ws.rs.core.*;

@Component
public class ErrorController {
    @Context
    private UriInfo uriInfo;

}
