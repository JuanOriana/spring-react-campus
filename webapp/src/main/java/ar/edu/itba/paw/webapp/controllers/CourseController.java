package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.common.assemblers.*;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Path("courses")
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
    private AuthFacade authFacade;

    @Autowired
    private ExamService examService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CourseAssembler courseAssembler;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private AnswerAssembler answerAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AnnouncementAssembler announcementAssembler;

    @Autowired
    private ExamAssembler examAssembler;

    @Autowired
    private ExamStatsAssembler examStatsAssembler;

    @Autowired
    private FileModelAssembler fileAssembler;

    @Autowired
    private TimetableService timetableService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @GET
    @Path("/{courseId}/announcements")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getAnnouncements(@PathParam("courseId") Long courseId,
                                     @QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        CampusPage<Announcement> announcements = announcementService.listByCourse(courseId, page, pageSize);
        if(announcements.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<AnnouncementDto>>(announcementAssembler.toResources(announcements.getContent())){});
        return PaginationBuilder.build(announcements, builder, uriInfo, pageSize);
    }

    @Path("/{courseId}/files")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/vnd.campus.api.v1+json")
    public Response postFile(@PathParam("courseId") Long courseId,
                             @FormDataParam("file") InputStream fileStream,
                             @FormDataParam("file") FormDataContentDisposition fileMetadata,
                             @FormDataParam("category") String category) throws IOException {
        if(category == null) {
            throw new BadRequestException();
        }
        File file = getFileFromStream(fileStream);
        if (file.length() == 0) throw new BadRequestException("No file was provided");
        Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
        FileModel fileModel = fileService.create(file.length(), fileMetadata.getFileName(), IOUtils.toByteArray(fileStream),
                course, Collections.singletonList(Long.parseLong(category)));
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + fileModel.getFileId());
        return Response.created(location).build();
    }


    @GET
    @Path("/{courseId}/files")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getFiles(@PathParam("courseId") Long courseId,
                             @QueryParam("category-type") List<Long> categoryType,
                             @QueryParam("extension-type") List<Long> extensionType,
                             @QueryParam("query") @DefaultValue("") String query,
                             @QueryParam("order-property") @DefaultValue("date") String orderProperty,
                             @QueryParam("order-direction") @DefaultValue("desc") String orderDirection,
                             @QueryParam("page") @DefaultValue("1") Integer page,
                             @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        categoryType = categoryType == null ? Collections.emptyList() : categoryType;
        extensionType = extensionType == null ? Collections.emptyList() : extensionType;
        CampusPage<FileModel> filePage = fileService.listByCourse(query, extensionType, categoryType,authFacade.getCurrentUserId(),courseId,
                page, pageSize, orderDirection, orderProperty);
        if (filePage.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<FileModelDto>>(fileAssembler.toResources(filePage.getContent())){});
        return PaginationBuilder.build(filePage, builder, uriInfo, pageSize);
    }


    private File getFileFromStream(InputStream in) throws IOException {
        File tmpFile = File.createTempFile("tmp", "file");
        tmpFile.deleteOnExit();
        try (FileOutputStream stream = new FileOutputStream(tmpFile)) {
            IOUtils.copy(in, stream);
        } catch (IOException e) {
            LOGGER.error("There was an error copying the file from the server");
        }
        return tmpFile;
    }

    @POST
    @Consumes("application/vnd.campus.api.v1+json")
    public Response postCourse(@Valid CourseFormDto courseForm) {
        if (courseForm == null) {
            throw new BadRequestException();
        }
        dtoValidator.validate(courseForm, "Invalid body request");
        Course course = courseService.create(courseForm.getYear(), courseForm.getQuarter(), courseForm.getBoard(),
                courseForm.getSubjectId(), courseForm.getStartTimes(), courseForm.getEndTimes());
        LOGGER.debug("Created course in year {} in quarter {} of subjectId {} with id {}", courseForm.getYear(),
                courseForm.getBoard(), courseForm.getSubjectId(), course.getCourseId());
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + course.getCourseId());
        return Response.created(location).build();
    }

    @GET
    @Path("/available-years")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getAvailableYears() {
        List<Integer> availableYears = courseService.getAvailableYears();
        return Response.ok(new GenericEntity<YearListDto>(new YearListDto(availableYears)){}).build();
    }

    // TODO: Paginate all courses if no year and quarter was sent
    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getCourses(@QueryParam("page") @DefaultValue("1")
                                       Integer page,
                               @QueryParam("pageSize") @DefaultValue("10")
                                       Integer pageSize,
                               @QueryParam("year")
                                       Integer year,
                               @QueryParam("quarter")
                                       Integer quarter) {
        if(year == null && quarter == null) {
            List<Course> courses = courseService.list();
            return Response.ok(new GenericEntity<List<CourseDto>>(courseAssembler.toResources(courses)){}).build();
        }
        year = year == null ? Calendar.getInstance().get(Calendar.YEAR) : year;
        if (quarter == null) {
            quarter = Calendar.getInstance().get(Calendar.MONTH) <= 6 ? 1 : 2;
        }
        CampusPage<Course> courses = courseService.listByYearQuarter(year, quarter, page, pageSize);
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<CourseDto>>(courseAssembler.toResources(courses.getContent())) {
                });
        return PaginationBuilder.build(courses, builder, uriInfo, pageSize);
    }

    @POST
    @Path("/{courseId}/announcements")
    @Consumes("application/vnd.campus.api.v1+json")
    public Response newAnnouncement(@PathParam("courseId") Long courseId,
                                    @Valid AnnouncementFormDto announcementDto) throws DtoValidationException {
        dtoValidator.validate(announcementDto, "Invalid body request");
        Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Announcement announcement = announcementService.create(announcementDto.getTitle(),
                announcementDto.getContent(),
                userService.findById(authFacade.getCurrentUserId()).orElseThrow(UserNotFoundException::new),
                course,
                uriInfo.getAbsolutePath().getPath());
        URI location = URI.create(uriInfo.getBaseUri() + "announcements/" + announcement.getAnnouncementId());
        LOGGER.debug("Announcement created on: {}", location.getPath());
        return Response.created(location).build();
    }

    @GET
    @Path("/{courseId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getCourseById(@PathParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
        return Response.ok(courseAssembler.toResource(course)).build();
    }

    @GET
    @Path("/{courseId}/teachers")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getCourseTeachers(@PathParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        List<User> courseTeachers = courseService.getTeachers(courseId);
        if (courseTeachers.isEmpty()) {
            return Response.noContent().build();
        }
        List<UserDto> teachers = userAssembler.toResources(courseTeachers);
        return Response.ok(new GenericEntity<List<UserDto>>(teachers) {
        }).build();
    }

    @GET
    @Path("/{courseId}/helpers")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getCourseHelpers(@PathParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        List<User> courseHelpers = courseService.getHelpers(courseId);
        if (courseHelpers.isEmpty()) {
            return Response.noContent().build();
        }
        List<UserDto> helpers = userAssembler.toResources(courseHelpers);
        return Response.ok(new GenericEntity<List<UserDto>>(helpers) {
        }).build();
    }

    @GET
    @Path("/{courseId}/students")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getCourseStudents(@QueryParam("page") @DefaultValue("1")
                                              Integer page,
                                      @QueryParam("page-size") @DefaultValue("10")
                                              Integer pageSize,
                                      @PathParam("courseId")
                                              Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        CampusPage<User> enrolledStudents = userService.getStudentsByCourse(courseId, page, pageSize);
        if (enrolledStudents.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<UserDto>>(userAssembler.toResources(enrolledStudents.getContent())) {
                });
        return PaginationBuilder.build(enrolledStudents, builder, uriInfo, pageSize);
    }

    @GET
    @Path("/{courseId}/exams")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getCourseExams(@PathParam("courseId") Long courseId) {
        List<Exam> exams = examService.listByCourse(courseId);
        if (exams.isEmpty()) {
            return Response.noContent().build();
        }
        if(courseService.isPrivileged(authFacade.getCurrentUserId(), courseId)) {
            return Response.ok(new GenericEntity<List<ExamStatsDto>>(examStatsAssembler.toResources(exams)){}).build();
        }
        List<ExamDto> examDtoList = examAssembler.toResources(exams);
        return Response.ok(new GenericEntity<List<ExamDto>>(examDtoList) {
        }).build();
    }

    @GET
    @Path("/{courseId}/timetable")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getTimetable(@PathParam("courseId") Long courseId) {
        Timetable[] schedule = timetableService.findByIdOrdered(courseId);
        if(schedule.length == 0) {
            return Response.noContent().build();
        }
        List<TimetableDto> timetable = Arrays.stream(schedule).map(TimetableDto::fromTimetable).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TimetableDto>>(timetable){}).build();
    }

    @POST
    @Path("/{courseId}/exams")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/vnd.campus.api.v1+json")
    public Response newExam(@PathParam("courseId") Long courseId,
                            @FormDataParam("file") InputStream fileStream,
                            @FormDataParam("file") FormDataContentDisposition fileMetadata,
                            @FormDataParam("metadata") String examMetadata) throws IOException {
        File file = getFileFromStream(fileStream);
        if (file.length() == 0 || examMetadata == null) throw new BadRequestException("No file or file metadata was provided");
        ExamFormDto examForm = objectMapper.readValue(examMetadata, ExamFormDto.class);
        Exam exam = examService.create(courseId, authFacade.getCurrentUserId(), examForm.getTitle(),
                examForm.getContent(), fileMetadata.getFileName(), IOUtils.toByteArray(fileStream),
                file.length(), LocalDateTime.parse(examForm.getStartTime()), LocalDateTime.parse(examForm.getEndTime()));
        URI location = URI.create(uriInfo.getBaseUri() + "/exams/" + exam.getExamId());
        LOGGER.debug("Created new exam on {}", location);
        return Response.created(location).build();
    }

    @GET
    @Path("/{courseId}/exams/solved")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getResolvedExams(@PathParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        Long userId = authFacade.getCurrentUserId();
        List<Exam> exams = examService.getResolvedExams(userId, courseId);
        if(exams.isEmpty()) {
            return Response.noContent().build();
        }
        List<ExamDto> examDtoList = exams
                .stream()
                .map(exam -> examAssembler.toResource(exam))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<ExamDto>>(examDtoList) {
        }).build();
    }

    @GET
    @Path("/{courseId}/exams/unsolved")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getUnresolvedExams(@PathParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        Long userId = authFacade.getCurrentUserId();
        List<Exam> exams = examService.getUnresolvedExams(userId, courseId);
        if(exams.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(new GenericEntity<List<ExamDto>>(examAssembler.toResources(exams)) {
        }).build();
    }

    @GET
    @Path("/{courseId}/exams/answers")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getCourseAnswers(@PathParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        Long userId = authFacade.getCurrentUserId();
        List<AnswerDto> answers = answerAssembler.toResources(answerService.getMarks(userId, courseId));
        return Response.ok(new GenericEntity<List<AnswerDto>>(answers) {
        }).build();
    }

    @GET
    @Path("/{courseId}/exams/average")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getCourseAverage(@PathParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        Long userId = authFacade.getCurrentUserId();
        Double average = answerService.getAverageOfUserInCourse(userId, courseId);
        return Response.ok(new GenericEntity<AverageDto>(new AverageDto(average)) {}).build();
    }
}
