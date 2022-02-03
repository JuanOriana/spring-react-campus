package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.webapp.dto.FileModelDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Path("files")
@Component
public class FileController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthFacade authFacade;

    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFiles(@QueryParam("category-type") List<Long> categoryType,
                             @QueryParam("extension-type") List<Long> extensionType,
                             @QueryParam("query") @DefaultValue("") String query,
                             @QueryParam("order-property") @DefaultValue("date") String orderProperty,
                             @QueryParam("order-direction") @DefaultValue("desc") String orderDirection,
                             @QueryParam("page") @DefaultValue("1") Integer page,
                             @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        categoryType = categoryType == null ? Collections.emptyList() : categoryType;
        extensionType = extensionType == null ? Collections.emptyList() : extensionType;
        CampusPage<FileModel> filePage = fileService.listByUser(query, extensionType, categoryType, authFacade.getCurrentUserId(),
                page, pageSize, orderDirection, orderProperty);
        if(filePage.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<FileModelDto>>(
                        filePage.getContent()
                                .stream()
                                .map(FileModelDto::fromFile)
                                .collect(Collectors.toList())){});
        return PaginationBuilder.build(filePage, builder, uriInfo, pageSize);
    }


}
