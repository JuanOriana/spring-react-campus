package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.AnnouncementDto;
import ar.edu.itba.paw.webapp.dto.AnnouncementFormDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


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
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private DtoConstraintValidator validator;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnouncementsController.class);

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getAnnouncements(@QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("pageSize") @DefaultValue("10") Integer pageSize,
                                     @QueryParam("courseId") Long courseId) {

        CampusPage<Announcement> announcements = courseId == null ?
                announcementService.listByUser(authFacade.getCurrentUserId(), page, pageSize) : announcementService.listByCourse(courseId, page, pageSize);

        if(announcements.isEmpty()) {
            return Response.noContent().build();
        }

        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<AnnouncementDto>>(
                        announcements.getContent()
                                .stream()
                                .map(AnnouncementDto::fromAnnouncement)
                                .collect(Collectors.toList())){});
        return PaginationBuilder.build(announcements, builder, uriInfo, pageSize);
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response postAnnouncement(@Valid AnnouncementFormDto announcementDto) {
        validator.validate(announcementDto, "Invalid body request");
        Long courseId = announcementDto.getCourseId();
        if(!courseService.isPrivileged(authFacade.getCurrentUserId(), courseId)) {
            throw new InsufficientAuthenticationException("Insufficient permissions");
        }
        Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + courseId);
        Announcement announcement = announcementService.create(announcementDto.getTitle(),
                                                               announcementDto.getContent(),
                                                               userService.findById(authFacade.getCurrentUserId()).orElseThrow(UserNotFoundException::new),
                                                               course,
                                                               location.getPath());
        LOGGER.debug("Creating announcement with id {} on courseId {} at path {}", announcement.getAnnouncementId(), courseId, location.getPath());
        return Response.created(location).build();
    }

    @DELETE
    @Path("/{announcementId}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response removeAnnouncement(@PathParam("announcementId") Long announcementId) {
        LOGGER.debug("Deleting announcement {}", announcementId);
        if(!announcementService.delete(announcementId)) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }

}
