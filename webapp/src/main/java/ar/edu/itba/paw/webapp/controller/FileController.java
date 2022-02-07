package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.exception.FileCategoryNotFoundException;
import ar.edu.itba.paw.models.exception.FileExtensionNotFoundException;
import ar.edu.itba.paw.models.exception.FileNotFoundException;
import ar.edu.itba.paw.webapp.assembler.FileCategoryAssembler;
import ar.edu.itba.paw.webapp.assembler.FileExtensionAssembler;
import ar.edu.itba.paw.webapp.assembler.FileModelAssembler;
import ar.edu.itba.paw.webapp.dto.FileCategoryDto;
import ar.edu.itba.paw.webapp.dto.FileExtensionDto;
import ar.edu.itba.paw.webapp.dto.FileModelDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

@Path("files")
@Component
public class FileController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private FileModelAssembler fileAssembler;

    @Autowired
    private FileCategoryAssembler fileCategoryAssembler;

    @Autowired
    private FileExtensionAssembler fileExtensionAssembler;

    @Autowired
    private FileExtensionService fileExtensionService;

    @Autowired
    private FileCategoryService fileCategoryService;

    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFiles(@QueryParam("category-type") List<Long> categoryType,
                             @QueryParam("extension-type") List<Long> extensionType,
                             @QueryParam("query") @DefaultValue("") String query,
                             @QueryParam("order-property") @DefaultValue("date") String orderProperty,
                             @QueryParam("order-direction") @DefaultValue("desc") String orderDirection,
                             @QueryParam("page") @DefaultValue("1") Integer page,
                             @QueryParam("page-size") @DefaultValue("10") Integer pageSize) {
        categoryType = categoryType == null ? Collections.emptyList() : categoryType;
        extensionType = extensionType == null ? Collections.emptyList() : extensionType;
        CampusPage<FileModel> filePage = fileService.listByUser(query, extensionType, categoryType, authFacade.getCurrentUserId(),
                page, pageSize, orderDirection, orderProperty);
        if(filePage.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<FileModelDto>>(fileAssembler.toResources(filePage.getContent())){});
        return PaginationBuilder.build(filePage, builder, uriInfo, pageSize);
    }

    @Path("/{fileId}")
    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFile(@PathParam("fileId") Long fileId) {
        FileModel file = fileService.findById(fileId).orElseThrow(FileNotFoundException::new);
        fileService.incrementDownloads(fileId);
        Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(file.getFile()));
        response.header("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"" );
        return response.build();
    }

    @DELETE
    @Path("/{fileId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response deleteFile(@PathParam("fileId") Long fileId) {
        if(!fileService.delete(fileId)) throw new FileNotFoundException();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Path("/categories")
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
    @Path("categories/{fileCategoryId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileCategory(@PathParam("fileCategoryId") Long fileCategoryId) {
        FileCategory fileCategory = fileCategoryService.findById(fileCategoryId).orElseThrow(FileCategoryNotFoundException::new);
        return Response.ok(new GenericEntity<FileCategoryDto>(fileCategoryAssembler.toResource(fileCategory)){}).build();
    }

    @Path("extensions")
    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileExtensions() {
        List<FileExtension> fileExtensions = fileExtensionService.getExtensions();
        return Response.ok(new GenericEntity<List<FileExtensionDto>>(fileExtensionAssembler.toResources(fileExtensions)){}).build();
    }

    @GET
    @Path("extensions/{fileExtensionId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileExtension(@PathParam("fileExtensionId") Long fileExtensionId) {
        FileExtension fileExtension = fileExtensionService.findById(fileExtensionId).orElseThrow(FileExtensionNotFoundException::new);
        return Response.ok(new GenericEntity<FileExtensionDto>(fileExtensionAssembler.toResource(fileExtension)){}).build();
    }


}
