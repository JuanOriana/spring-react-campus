package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.AnnouncementDto;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private DtoConstraintValidator DtoValidator;

    @Autowired
    private AnnouncementService announcementService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsControllerREST.class);



    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getAllAnnouncements(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize){
        Object userPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CampusUser user = (CampusUser) userPrincipal;
        List<Announcement> list = announcementService.listByUser(user.getUserId(),page,pageSize ).getContent();

        if(list.isEmpty()){
            return Response.noContent().build();
        }

        return Response.ok(new GenericEntity<List<AnnouncementDto>>(list.stream().map(AnnouncementDto::fromAnnouncement).collect(Collectors.toList())){}).build();
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
