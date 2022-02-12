package ar.edu.itba.paw.webapp.controllers;


import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.exception.AnnouncementNotFoundException;
import ar.edu.itba.paw.webapp.common.assemblers.AnnouncementAssembler;
import ar.edu.itba.paw.webapp.dto.announcement.AnnouncementDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Component
@Path("/announcements")
public class AnnouncementsController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private AnnouncementAssembler assembler;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);

    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getAnnouncements(@QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("page-size") @DefaultValue("10") Integer pageSize) {
        CampusPage<Announcement> announcements = announcementService.listByUser(authFacade.getCurrentUserId(), page, pageSize);
        if(announcements.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<AnnouncementDto>>(assembler.toResources(announcements.getContent(), false)){});
        return PaginationBuilder.build(announcements, builder, uriInfo, pageSize);
    }

    @GET
    @Path("/{announcementId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getAnnouncement(@PathParam("announcementId") Long announcementId) {
        Announcement announcement = announcementService.findById(announcementId).orElseThrow(AnnouncementNotFoundException::new);
        return Response.ok(new GenericEntity<AnnouncementDto>(assembler.toResource(announcement, false)){}).build();
    }

    @DELETE
    @Path("/{announcementId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response removeAnnouncement(@PathParam("announcementId") Long announcementId) {
        LOGGER.debug("Deleting announcement {}", announcementId);
        if(!announcementService.delete(announcementId)) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }

}
