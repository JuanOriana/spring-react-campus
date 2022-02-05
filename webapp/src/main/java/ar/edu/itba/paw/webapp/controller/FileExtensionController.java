package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.models.exception.FileExtensionNotFoundException;
import ar.edu.itba.paw.webapp.assembler.FileExtensionAssembler;
import ar.edu.itba.paw.webapp.dto.FileExtensionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("file-extensions")
public class FileExtensionController {

    @Autowired
    private FileExtensionService fileExtensionService;

    @Autowired
    private FileExtensionAssembler fileExtensionAssembler;

    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileExtensions() {
        List<FileExtension> fileExtensions = fileExtensionService.getExtensions();
        return Response.ok(new GenericEntity<List<FileExtensionDto>>(fileExtensionAssembler.toResources(fileExtensions)){}).build();
    }

    @GET
    @Path("/{fileExtensionId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileExtension(@PathParam("fileExtensionId") Long fileExtensionId) {
        FileExtension fileExtension = fileExtensionService.findById(fileExtensionId).orElseThrow(FileExtensionNotFoundException::new);
        return Response.ok(new GenericEntity<FileExtensionDto>(fileExtensionAssembler.toResource(fileExtension)){}).build();
    }
}
