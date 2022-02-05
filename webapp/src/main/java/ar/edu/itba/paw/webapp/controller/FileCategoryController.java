package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.exception.FileCategoryNotFoundException;
import ar.edu.itba.paw.webapp.assembler.FileCategoryAssembler;
import ar.edu.itba.paw.webapp.dto.FileCategoryDto;
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
@Path("file-categories")
public class FileCategoryController {

    @Autowired
    private FileCategoryAssembler fileCategoryAssembler;

    @Autowired
    private FileCategoryService fileCategoryService;

    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileCategories() {
        List<FileCategory> fileCategories = fileCategoryService.getCategories();
        if(fileCategories.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(new GenericEntity<List<FileCategoryDto>>(fileCategoryAssembler.toResources(fileCategories)){}).build();
    }

    @GET
    @Path("/{fileCategoryId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileCategory(@PathParam("fileCategoryId") Long fileCategoryId) {
        FileCategory fileCategory = fileCategoryService.findById(fileCategoryId).orElseThrow(FileCategoryNotFoundException::new);
        return Response.ok(new GenericEntity<FileCategoryDto>(fileCategoryAssembler.toResource(fileCategory)){}).build();
    }
}
