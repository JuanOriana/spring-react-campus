package ar.edu.itba.paw.webapp.controllers;

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
import ar.edu.itba.paw.webapp.common.assemblers.FileCategoryAssembler;
import ar.edu.itba.paw.webapp.common.assemblers.FileExtensionAssembler;
import ar.edu.itba.paw.webapp.common.assemblers.FileModelAssembler;
import ar.edu.itba.paw.webapp.common.models.ParamLongList;
import ar.edu.itba.paw.webapp.dto.file.FileCategoryDto;
import ar.edu.itba.paw.webapp.dto.file.FileExtensionDto;
import ar.edu.itba.paw.webapp.dto.file.FileModelDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

@Path("/api/files")
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
    public Response getFiles(@QueryParam("category-type") ParamLongList categoryType,
                             @QueryParam("extension-type") ParamLongList extensionType,
                             @DefaultValue("") @QueryParam("query") String query,
                             @DefaultValue("date") @QueryParam("order-property") String orderProperty,
                             @DefaultValue("desc") @QueryParam("order-direction") String orderDirection,
                             @DefaultValue("1") @QueryParam("page") Integer page,
                             @DefaultValue("10") @QueryParam("page-size") Integer pageSize) {
        List<Long> categories = categoryType == null ? Collections.emptyList() : categoryType.getValues();
        List<Long> extensions = extensionType == null ? Collections.emptyList() : extensionType.getValues();
        CampusPage<FileModel> filePage = fileService.listByUser(query, extensions, categories, authFacade.getCurrentUserId(),
                page, pageSize, orderDirection, orderProperty);
        if(filePage.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<FileModelDto>>(fileAssembler.toResources(filePage.getContent(), false)){});
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
    @Path("/categories/{fileCategoryId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileCategory(@PathParam("fileCategoryId") Long fileCategoryId) {
        FileCategory fileCategory = fileCategoryService.findById(fileCategoryId).orElseThrow(FileCategoryNotFoundException::new);
        return Response.ok(new GenericEntity<FileCategoryDto>(fileCategoryAssembler.toResource(fileCategory)){}).build();
    }

    @Path("/extensions")
    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileExtensions() {
        List<FileExtension> fileExtensions = fileExtensionService.getExtensions();
        return Response.ok(new GenericEntity<List<FileExtensionDto>>(fileExtensionAssembler.toResources(fileExtensions)){}).build();
    }

    @GET
    @Path("/extensions/{fileExtensionId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFileExtension(@PathParam("fileExtensionId") Long fileExtensionId) {
        FileExtension fileExtension = fileExtensionService.findById(fileExtensionId).orElseThrow(FileExtensionNotFoundException::new);
        return Response.ok(new GenericEntity<FileExtensionDto>(fileExtensionAssembler.toResource(fileExtension)){}).build();
    }


}
