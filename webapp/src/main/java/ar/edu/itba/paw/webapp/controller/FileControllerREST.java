package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.models.exception.FileNotFoundException;
import ar.edu.itba.paw.webapp.dto.FileCampusPageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Path("files")
@Component
public class FileControllerREST {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilesController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private FileCategoryService fileCategoryService;

    @Autowired
    private FileExtensionService fileExtensionService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response allUserFiles(@DefaultValue("") @QueryParam("category-type") List<Long> categoryType,
                          @DefaultValue("") @QueryParam("extension-type") List<Long> extensionType,
                          @DefaultValue("") @QueryParam("query") String query,
                          @DefaultValue("date") @QueryParam("order-property") String orderProperty,
                          @DefaultValue("desc") @QueryParam("order-direction") String orderDirection,
                          @DefaultValue("1") @QueryParam("page") Integer page,
                          @DefaultValue("10") @QueryParam("pageSize") Integer pageSize) {

        //User user = authFacade.getCurrentUser(); //TODO: ver como pasarle el userId actual
        //BORRAR
        long userId = 3;

        List<Long> fileCategoryList = categoryType;
        if (categoryType.size() <= 1){
            fileCategoryList = fileCategoryService.getCategories().stream().map(FileCategory::getCategoryId).collect(Collectors.toList());
        }
        List<Long> fileExtensionList = extensionType;
        if (extensionType.size() <= 1){
            fileExtensionList = fileExtensionService.getExtensions().stream().map(FileExtension::getFileExtensionId).collect(Collectors.toList());
        }

        CampusPage<FileModel> filePage = fileService.listByUser(query, fileExtensionList, fileCategoryList, userId,
                page, pageSize, orderDirection, orderProperty);

        return Response.ok(
                FileCampusPageDto.fromCampusPage(filePage))
                .build();
    }

    @GET
    @Path("/course/{courseId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response allCourseFiles(@PathParam("courseId") Long courseId,
                                   @DefaultValue("") @QueryParam("category-type") List<Long> categoryType,
                                 @DefaultValue("") @QueryParam("extension-type") List<Long> extensionType,
                                 @DefaultValue("") @QueryParam("query") String query,
                                 @DefaultValue("date") @QueryParam("order-property") String orderProperty,
                                 @DefaultValue("desc") @QueryParam("order-direction") String orderDirection,
                                 @DefaultValue("1") @QueryParam("page") Integer page,
                                 @DefaultValue("10") @QueryParam("pageSize") Integer pageSize) {

        //User user = authFacade.getCurrentUser(); //TODO: ver como pasarle el userId actual
        //BORRAR
        long userId = 3;

        List<Long> fileCategoryList = categoryType;
        if (categoryType.size() <= 1){
            fileCategoryList = fileCategoryService.getCategories().stream().map(FileCategory::getCategoryId).collect(Collectors.toList());
        }
        List<Long> fileExtensionList = extensionType;
        if (extensionType.size() <= 1){
            fileExtensionList = fileExtensionService.getExtensions().stream().map(FileExtension::getFileExtensionId).collect(Collectors.toList());
        }

        CampusPage<FileModel> filePage = fileService.listByCourse(query, fileExtensionList, fileCategoryList,
                userId, courseId, page, pageSize, orderDirection, orderProperty);

        return Response.ok(
                FileCampusPageDto.fromCampusPage(filePage))
                .build();
    }

    @GET
    @Path("/{fileId}")
    @Produces(value = {MediaType.APPLICATION_OCTET_STREAM})
    public Response downloadFile(@PathParam("fileId") Long fileId) {
        FileModel file = fileService.findById(fileId).orElseThrow(FileNotFoundException::new);
        InputStream is = new ByteArrayInputStream(file.getFile());
        fileService.incrementDownloads(fileId);
        if (!file.getExtension().getFileExtensionName().equals("pdf")) {
            return Response.ok(is, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
                    .build();
        }
        else {
            Response.ResponseBuilder response = Response.ok(is);
            response.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" );
            response.type("application/pdf");
            return response.build();
        }
    }

    @DELETE
    @Path("/{fileId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteFile(@PathParam("fileId") Long fileId) {
        LOGGER.debug("Deleting file {}", fileId);
        boolean removedSuccessfully = fileService.delete(fileId);
        if (removedSuccessfully){
            return Response.status(Response.Status.NO_CONTENT).build(); //Devuelve un 204 sin contenido si logro borrarlo
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
