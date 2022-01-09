package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileCategoryService;
import ar.edu.itba.paw.interfaces.FileExtensionService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.webapp.dto.FileCampusPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("files")
@Component
public class FileControllerREST {

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
    public Response files(@DefaultValue("") @QueryParam("category-type") List<Long> categoryType,
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
}
