package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.AnnouncementDto;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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


    @GET
    @Path("/all")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getAllAnnouncements(){
        Object userPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CampusUser user = (CampusUser) userPrincipal;
        List<Announcement> list = announcementService.listByUser(user.getUserId(),1,10 ).getContent();
        return Response.ok(new GenericEntity<List<AnnouncementDto>>(list.stream().map(AnnouncementDto::fromAnnouncement).collect(Collectors.toList())){}).build();
    }
}
