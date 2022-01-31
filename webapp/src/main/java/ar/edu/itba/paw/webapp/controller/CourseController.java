package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.models.exception.FileNotFoundException;
import ar.edu.itba.paw.models.exception.UserNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
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
import java.io.*;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Path("/{courseId}/files")
    @POST
    @Consumes(value = MediaType.MULTIPART_FORM_DATA)
    public Response postFile(@PathParam("courseId") Long courseId,
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

    @Path("/{courseId}/files/{fileId}")
    @GET
    @Produces(value = MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("courseId") Long courseId,
                                 @PathParam("fileId") Long fileId) {
        FileModel file = fileService.findById(fileId).orElseThrow(FileNotFoundException::new);
        fileService.incrementDownloads(fileId);
        Response.ResponseBuilder response = Response.ok(new ByteArrayInputStream(file.getFile()));
        response.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" );
        return response.build();
    }

    @DELETE
    @Path("/{courseId}/files/{fileId}")
    public Response deleteFile(@PathParam("courseId") Long courseId,
                               @PathParam("fileId") Long fileId) {
        if(!fileService.delete(fileId)) throw new FileNotFoundException();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private File getFileFromStream(InputStream in) throws IOException {
        File tmpFile = File.createTempFile("tmp", "file");
        tmpFile.deleteOnExit();
        try(FileOutputStream stream = new FileOutputStream(tmpFile)){
            IOUtils.copy(in, stream);
        } catch (IOException e) {
            LOGGER.error("There was an error copying the file from the server");
        }
        return tmpFile;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCourse(@Valid CourseFormDto courseForm)
            throws DtoValidationException {
        if(courseForm == null) {
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
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getAvailableYears() {
        List<Integer> availableYears = courseService.getAvailableYears();
        List<AvailableYearsDto> availableYearsDtoList = availableYears.stream()
                .map(AvailableYearsDto::fromYear)
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<AvailableYearsDto>>(availableYearsDtoList){}).build();
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCourses(@QueryParam("page") @DefaultValue("1")
                                       Integer page,
                               @QueryParam("pageSize") @DefaultValue("10")
                                       Integer pageSize,
                               @QueryParam("year")
                                       Integer year,
                               @QueryParam("quarter")
                                       Integer quarter) {
        year = year == null ? Calendar.getInstance().get(Calendar.YEAR) : year;
        if (quarter == null) {
            quarter = Calendar.getInstance().get(Calendar.MONTH) <= 6 ? 1 : 2;
        }
        CampusPage<Course> courses = courseService.listByYearQuarter(year, quarter, page, pageSize);
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<CourseDto>>(
                        courses.getContent()
                                .stream()
                                .map(CourseDto::fromCourse)
                                .collect(Collectors.toList())) {});
        return PaginationBuilder.build(courses, builder, uriInfo, pageSize);
    }

    @POST
    @Path("/{courseId}/announcements")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newAnnouncement(@PathParam("courseId") Long courseId,
                                    @Valid AnnouncementFormDto announcementDto) throws DtoValidationException {
        Long userId = authFacade.getCurrentUserId();
        if(!courseService.isPrivileged(userId, courseId)) {
            throw new ForbiddenException();
        }
        dtoValidator.validate(announcementDto, "Invalid body request");
        Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Announcement announcement = announcementService.create(announcementDto.getTitle(),
                announcementDto.getContent(),
                userService.findById(userId).orElseThrow(UserNotFoundException::new),
                course,
                uriInfo.getAbsolutePath().getPath());
        URI location = URI.create(uriInfo.getBaseUri() + "/announcements/" + announcement.getAnnouncementId());
        LOGGER.debug("Announcement created on: {}", location.getPath());
        return Response.created(location).build();
    }

    @GET
    @Path("/{courseId}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCourseById(@PathParam("courseId") Long courseId) {
        if (courseId == null) {
            throw new BadRequestException();
        }
        Course course = courseService.findById(courseId).orElseThrow(CourseNotFoundException::new);
        return Response.ok(CourseDto.fromCourse(course)).build();
    }

    @GET
    @Path("/{courseId}/teachers")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCourseTeachers(@PathParam("courseId") Long courseId) {
        if(courseId == null) {
            throw new BadRequestException();
        }
        List<User> courseTeachers = courseService.getTeachers(courseId);
        if (courseTeachers.isEmpty()) {
            return Response.noContent().build();
        }
        List<UserDto> teachers = courseTeachers.stream().map(UserDto::fromUser).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<UserDto>>(teachers){}).build();
    }

    @GET
    @Path("/{courseId}/helpers")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCourseHelpers(@PathParam("courseId") Long courseId) {
        if(courseId == null) {
            throw new BadRequestException();
        }
        List<User> courseHelpers = courseService.getHelpers(courseId);
        if (courseHelpers.isEmpty()) {
            return Response.noContent().build();
        }
        List<UserDto> helpers = courseHelpers.stream().map(UserDto::fromUser).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<UserDto>>(helpers){}).build();
    }

    @GET
    @Path("/{courseId}/students")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCourseStudents(@QueryParam("page") @DefaultValue("1")
                                                  Integer page,
                                      @QueryParam("pageSize") @DefaultValue("10")
                                              Integer pageSize,
                                      @PathParam("courseId")
                                                  Long courseId) {
        if(courseId == null) {
            throw new BadRequestException();
        }
        CampusPage<User> enrolledStudents = userService.getStudentsByCourse(courseId, page, pageSize);
        if (enrolledStudents.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<UserDto>>(
                        enrolledStudents.getContent()
                                .stream()
                                .map(UserDto::fromUser)
                                .collect(Collectors.toList())){});
        return PaginationBuilder.build(enrolledStudents, builder, uriInfo, pageSize);
    }

    @GET
    @Path("/{courseId}/exams")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCourseExams(@PathParam("courseId") Long courseId) {
        List<Exam> exams = examService.listByCourse(courseId);
        if(exams.isEmpty()) {
            return Response.noContent().build();
        }
        List<ExamDto> examDtoList = exams.stream()
                .map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId())))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<ExamDto>>(examDtoList){}).build();
    }

    @POST
    @Path("/{courseId}/exams")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response newExam(@PathParam("courseId") Long courseId,
                            @Valid ExamFormDto examFormDto) throws DtoValidationException {
        Long userId = authFacade.getCurrentUserId();
        if(!courseService.isPrivileged(userId, courseId)){
            throw new ForbiddenException();
        }
        dtoValidator.validate(examFormDto, "Invalid body request");
        Exam exam = examService.create(courseId, examFormDto.getTitle(), examFormDto.getContent(),
                examFormDto.getFile().getOriginalFilename(), examFormDto.getFile().getBytes(),
                examFormDto.getFile().getSize(), LocalDateTime.parse(examFormDto.getStartTime()),
                LocalDateTime.parse(examFormDto.getEndTime()));
        LOGGER.debug("User with id {} created exam with id {}", userId, exam.getExamId());
        URI location = URI.create(uriInfo.getBaseUri() + "/exams/" + exam.getExamId());
        return Response.created(location).build();
    }

    @GET
    @Path("/{courseId}/exams/solved")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getResolvedExams(@PathParam("courseId") Long courseId) {
        if(courseId == null) {
            throw new BadRequestException();
        }
        Long userId = authFacade.getCurrentUserId();
        if(courseService.isPrivileged(userId, courseId)) {
            Map<Exam,Double> examAverage = examService.getExamsAverage(courseId);
            List<ExamDto> examDtoList = new ArrayList<>();
            examAverage.forEach((exam,average) -> examDtoList.add(ExamDto.fromExam(uriInfo, exam, average)));
            return Response.ok(new GenericEntity<List<ExamDto>>(examDtoList){}).build();
        }
        List<ExamDto> exams = examService.getResolvedExams(userId, courseId)
                .stream()
                .map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId())))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<ExamDto>>(exams){}).build();
    }

    @GET
    @Path("/{courseId}/exams/unsolved")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getUnresolvedExams(@PathParam("courseId") Long courseId) {
        if(courseId == null) {
            throw new BadRequestException();
        }
        Long userId = authFacade.getCurrentUserId();
        if(!courseService.isPrivileged(userId, courseId)) {
            throw new ForbiddenException();
        }
        List<ExamDto> exams = examService.getUnresolvedExams(userId, courseId)
                .stream()
                .map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId())))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<ExamDto>>(exams){}).build();
    }

    @GET
    @Path("/{courseId}/exams/answers")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCourseAnswers(@PathParam("courseId") Long courseId) {
        if(courseId == null) {
            throw new BadRequestException();
        }
        Long userId = authFacade.getCurrentUserId();
        if(!courseService.isPrivileged(userId, courseId)){
            throw new ForbiddenException();
        }
        List<AnswerDto> answers = answerService.getMarks(userId, courseId)
                .stream()
                .map(answer->AnswerDto.fromAnswer(uriInfo, answer))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<AnswerDto>>(answers){}).build();
    }

    @GET
    @Path("/{courseId}/exams/average")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getCourseAverage(@PathParam("courseId") Long courseId) {
        if(courseId == null) {
            throw new BadRequestException();
        }
        Long userId = authFacade.getCurrentUserId();
        if(!courseService.isPrivileged(userId, courseId)) {
            throw new ForbiddenException();
        }
        Double average = answerService.getAverageOfUserInCourse(userId, courseId);
        return Response.ok(new GenericEntity<Double>(average){}).build();
    }
}
