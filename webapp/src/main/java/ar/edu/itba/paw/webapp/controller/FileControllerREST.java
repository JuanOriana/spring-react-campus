package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.FileModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("files")
@Component
public class FileControllerREST {

    @Autowired
    private FileService fileService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response listFiles() {
        final List<FileModel> allFiles = fileService.list((long)3);
        return Response.ok(
                FileModelDto.fromFile(allFiles.get(0)))
        .build();
    }
}
