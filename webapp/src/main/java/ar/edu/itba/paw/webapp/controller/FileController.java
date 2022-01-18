package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("files")
@Component
public class FileController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    FileService fileService;

    @Autowired
    CourseService courseService;


    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response getFiles() {
        return Response.ok().build();
    }


}
