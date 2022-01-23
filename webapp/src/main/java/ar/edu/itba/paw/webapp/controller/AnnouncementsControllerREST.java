package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.webapp.dto.AnnouncementDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Path("/announcements")
@ComponentScan({"ar.edu.itba.paw.webapp.constraint.validator"})
public class AnnouncementsControllerREST {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private ResponsePaging<Announcement> announcementResponsePaging;


    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsControllerREST.class);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getAllAnnouncements(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize){
        Long userId = authFacade.getCurrentUserId();

        if(userId==null){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        CampusPage<Announcement> announcementsPaginated = announcementService.listByUser(userId,page,pageSize );

        if(announcementsPaginated.getContent().isEmpty()){
            return Response.noContent().build();
        }
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AnnouncementDto>>(announcementsPaginated.getContent().stream().map(AnnouncementDto::fromAnnouncement).collect(Collectors.toList())){});

        announcementResponsePaging.paging(announcementsPaginated, response, uriInfo, pageSize);

        return response.build();
    }

    @DELETE
    @Path("/{announcementId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response removeAnnouncement(@PathParam("announcementId") Long announcementId){
        LOGGER.debug("Deleting announcement {}", announcementId);

        if(announcementService.delete(announcementId)){
            return Response.ok().build();
        }else{
            return Response.noContent().build();
        }
    }




}
