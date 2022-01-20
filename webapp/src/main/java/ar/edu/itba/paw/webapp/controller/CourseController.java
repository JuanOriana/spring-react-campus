package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.FileService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.models.exception.FileNotFoundException;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.AnnouncementDto;
import ar.edu.itba.paw.webapp.dto.AnnouncementFormDto;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Path("courses/{courseId}")
public class CourseController {
    @Context
    private UriInfo uriInfo;

    @Autowired
    private DtoConstraintValidator dtoValidator;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);


    private Long getCurrentUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CampusUser) {
            return ((CampusUser)principal).getUserId();
        } else {
            return null;
        }
    }

    @Path("/files")
    @POST
    @Consumes(value = MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@PathParam("courseId") Long courseId,
                               @FormDataParam("file") InputStream fileStream,
                               @FormDataParam("file") FormDataContentDisposition fileMetadata) throws IOException {
        File file = getFileFromStream(fileStream);
        if(file.length() == 0) throw new BadRequestException("No file was provided");
        Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
        FileModel fileModel = fileService.create(file.length(), fileMetadata.getFileName(), IOUtils.toByteArray(fileStream),
                course);
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + fileModel.getFileId());
        return Response.created(location).build();
    }



    @Path("/files/{fileId}")
    @GET
    @Produces(value = MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("courseId") Long courseId,
                                 @PathParam("fileId") Long fileId) {
        FileModel file = fileService.findById(fileId).orElseThrow(FileNotFoundException::new);
        fileService.incrementDownloads(fileId);
        Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(file.getFile()));
        response.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" );
        return response.build();
    }

    @Path("/files/{fileId}")
    @DELETE
    public Response deleteFile(@PathParam("courseId") Long courseId,
                               @PathParam("fileId") Long fileId) {
        if(!fileService.delete(fileId)) throw new FileNotFoundException();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private File getFileFromStream(InputStream in) throws IOException {
        File tmpFile = File.createTempFile("tmp", "file");
        tmpFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tmpFile);
        IOUtils.copy(in, out);
        return tmpFile;
    }

    @GET
    @Path("/announcements")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getAnnouncements(@PathParam("courseId") Long courseId,@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize){
        CampusPage<Announcement> announcementsPaginated = announcementService.listByCourse(courseId,page,pageSize );
        if(announcementsPaginated.getContent().isEmpty()){
            return Response.noContent().build();
        }

        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AnnouncementDto>>(announcementsPaginated.getContent().stream().map(AnnouncementDto::fromAnnouncement).collect(Collectors.toList())){})
                                            .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).queryParam("pageSize", pageSize).build().toString(), "first")
                                            .link(uriInfo.getAbsolutePathBuilder().queryParam("page", announcementsPaginated.getTotal()).queryParam("pageSize", pageSize).build().toString(), "last");

        if(announcementsPaginated.getPage() != announcementsPaginated.getTotal()){
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page",(announcementsPaginated.getPage() < (announcementsPaginated.getTotal()/announcementsPaginated.getSize()))? announcementsPaginated.getPage() + 1: announcementsPaginated.getPage()).queryParam("pageSize", pageSize).build().toString(), "next");

        }

        if(announcementsPaginated.getPage() > 1){
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", Math.max(announcementsPaginated.getPage() - 1, 1)).queryParam("pageSize", pageSize).build().toString(), "prev");

        }
        return response.build();
    }

    @POST
    @Path("/announcements")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newAnnouncement(@PathParam("courseId") Long courseId, @Valid AnnouncementFormDto announcementFormDto) throws DtoValidationException{
        Long userId = getCurrentUserId();

        if(userId==null){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(courseService.isPrivileged(userId, courseId)){
            dtoValidator.validate(announcementFormDto, "Failed to validate new announcement attributes");
            Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
            URI location = URI.create(uriInfo.getAbsolutePath() + "/course/" + courseId);
            Announcement announcement = announcementService.create(announcementFormDto.getTitle(),announcementFormDto.getContent(),userService.findById(userId).orElseThrow(UserNotFoundException::new),course,location.getPath());

            LOGGER.debug("Announcement created by user with id:{} ", userId);

            return Response.ok(AnnouncementDto.fromAnnouncement(announcement)).status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourse(@PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
            return Response.ok( CourseDto.fromCourse(course) ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/teachers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourseTeachers(@PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            List<User> courseTeachers = courseService.getTeachers(courseId);

            if (courseTeachers.isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

            return Response.ok( new GenericEntity<List<UserDto>>(courseTeachers.stream().map(UserDto::fromUser).collect(Collectors.toList()) ){}).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/helpers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourseHelpers(@PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            List<User> courseHelpers = courseService.getHelpers(courseId);

            if (courseHelpers.isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

            return Response.ok( new GenericEntity<List<UserDto>>(courseHelpers.stream().map(UserDto::fromUser).collect(Collectors.toList()) ){} ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/students")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourseStudents(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize, @PathParam("courseId") Long courseId) throws DtoValidationException{
        if (courseId != null){
            CampusPage<User> enrolledStudents = userService.getStudentsByCourse(courseId, page, pageSize);

            if (enrolledStudents.getContent().isEmpty()){
                return Response.ok(Response.Status.NO_CONTENT).build();
            }

            return Response.ok( new GenericEntity<List<UserDto>>(enrolledStudents.getContent().stream().map(UserDto::fromUser).collect(Collectors.toList()) ){} )
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", enrolledStudents.getPage() + 1).queryParam("pageSize", pageSize).queryParam("courseId", courseId).build().toString(), "next") //TODO: condicionar esto a si es que tiene un next
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", Math.max(enrolledStudents.getPage() - 1, 1)).queryParam("pageSize", pageSize).queryParam("courseId", courseId).build().toString(), "prev")
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).queryParam("pageSize", pageSize).queryParam("courseId", courseId).build().toString(), "first")
                    .link(uriInfo.getAbsolutePathBuilder().queryParam("page", enrolledStudents.getTotal()).queryParam("pageSize", pageSize).queryParam("courseId", courseId).build().toString(), "last")
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

//    @GET
//    @Path("/schedule")
//    @Produces(value={MediaType.APPLICATION_JSON,})
//    public Response getSchedule(@PathParam("courseId") Long courseId){
//
//
//
//    }




}
