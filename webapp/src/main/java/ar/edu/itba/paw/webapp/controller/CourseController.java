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
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
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

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private ExamService examService;

    @Autowired
    private ResponsePaging<Announcement> announcementResponsePaging;

    @Autowired
    private ResponsePaging<User> userResponsePaging;

    @Autowired
    private ResponsePaging<Course> courseResponsePaging;

    @Autowired
    private AnswerService answerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

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

    @POST
    @Path("/course/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newCourse(@Valid CourseFormDto courseForm) throws DtoValidationException, URISyntaxException { //TODO: startTimes y endTimes

        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(courseForm != null) {
            Course course = courseService.create(courseForm.getYear(), courseForm.getQuarter(), courseForm.getBoard()
                    , courseForm.getSubjectId(), courseForm.getStartTimes(), courseForm.getEndTimes());
            LOGGER.debug("Created course in year {} in quarter {} of subjectId {} with id {}", courseForm.getYear(),
                    courseForm.getBoard(), courseForm.getSubjectId(), course.getCourseId());
            URI enrollUsers = new URI(uriInfo.getBaseUri().normalize() + "admin/course/" + course.getCourseId() + "/enroll"); //TODO: rev si termina quedando asi
            return Response.seeOther(enrollUsers).status(Response.Status.SEE_OTHER).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/courses")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCourses(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize, @QueryParam("year") Integer year, @QueryParam("quarter") Integer quarter) {

        if (!authFacade.isAdminUser()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        CampusPage<Course> coursesPaginated;
        List<Integer> availableYears = courseService.getAvailableYears();

        if (year == null && quarter == null) {
            List<Course> coursesList = courseService.list();

            if (coursesList.isEmpty()) {
                return Response.ok().status(Response.Status.NO_CONTENT).build();
            }

            coursesList.sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());
            return Response.ok(AllCoursesResponseDto.responseFrom(availableYears, coursesList)).build(); //TODO: paginacion?
        } else {
            if (year == null) {
                year = Calendar.getInstance().get(Calendar.YEAR);
            }
            if (quarter == null) {
                if (Calendar.getInstance().get(Calendar.MONTH) <= 6) {
                    quarter = 1;
                } else {
                    quarter = 2;
                }
            }
            coursesPaginated = courseService.listByYearQuarter(year, quarter, page, pageSize);

            if (coursesPaginated.getContent().isEmpty()) {
                return Response.ok().status(Response.Status.NO_CONTENT).build();
            }

            coursesPaginated.getContent().sort(Comparator.comparing(Course::getYear).thenComparing(Course::getQuarter).reversed());

            Response.ResponseBuilder response = Response.ok(new GenericEntity<List<CourseDto>>(coursesPaginated.getContent().stream().map(CourseDto::fromCourse).collect(Collectors.toList())) {
            });

            courseResponsePaging.paging(coursesPaginated, response, uriInfo, pageSize);

            return response.build(); //TODO: este CourseDto estaria bueno que tenga el link al enroll de ese curso
        }
    }

    @GET
    @Path("/announcements")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getAnnouncements(@PathParam("courseId") Long courseId,@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize){
        CampusPage<Announcement> announcementsPaginated = announcementService.listByCourse(courseId,page,pageSize );
        if(announcementsPaginated.getContent().isEmpty()){
            return Response.noContent().build();
        }

        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AnnouncementDto>>(announcementsPaginated.getContent().stream().map(AnnouncementDto::fromAnnouncement).collect(Collectors.toList())){});

        announcementResponsePaging.paging(announcementsPaginated, response, uriInfo, pageSize);

        return response.build();
    }

    @POST
    @Path("/announcements")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response newAnnouncement(@PathParam("courseId") Long courseId, @Valid AnnouncementFormDto announcementFormDto) throws DtoValidationException{
        Long userId = authFacade.getCurrentUserId();

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
        return Response.status(Response.Status.FORBIDDEN).build();
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

            Response.ResponseBuilder response = Response.ok( new GenericEntity<List<UserDto>>(enrolledStudents.getContent().stream().map(UserDto::fromUser).collect(Collectors.toList()) ){} );

            userResponsePaging.paging(enrolledStudents, response, uriInfo, pageSize);

            return response.build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



    @GET
    @Path("/exams")
    @Produces(value={MediaType.APPLICATION_JSON,})
    public Response getCourseExams(@PathParam("courseId") Long courseId){
        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.listByCourse(courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }



    @POST
    @Path("/exams")
    @Consumes(value={MediaType.APPLICATION_JSON,})
    @Produces(value ={MediaType.APPLICATION_JSON,})
    public Response newExam(@Valid ExamFormDto examFormDto,@PathParam("courseId") Long courseId){
        Long userId = authFacade.getCurrentUserId();

        if(!courseService.isPrivileged(userId,courseId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        dtoValidator.validate(examFormDto, "Failed to validate new exam attributes");
        Exam exam = examService.create(courseId, examFormDto.getTitle(), examFormDto.getContent(),
                examFormDto.getFile().getOriginalFilename(), examFormDto.getFile().getBytes(),
                examFormDto.getFile().getSize(), LocalDateTime.parse(examFormDto.getStartTime()),
                LocalDateTime.parse(examFormDto.getEndTime()));

        LOGGER.debug("User with id {} created exam with id {}",userId,exam.getExamId());

        return Response.ok(ExamDto.fromExam(uriInfo,exam,examService.getAverageScoreOfExam(exam.getExamId()))).build();
    }



    @GET
    @Path("/exams/solved")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getResolvedExams(@PathParam("courseId") Long courseId){
        Long userId = authFacade.getCurrentUserId();

        if(courseService.isPrivileged(userId, courseId) || !courseService.belongs(userId, courseId)){
            Map<Exam,Double> examAverage = examService.getExamsAverage(courseId);

            List<ExamDto> examsAverageDtos = new ArrayList<>();

            examAverage.forEach((exam,average) -> examsAverageDtos.add(ExamDto.fromExam(uriInfo, exam, average)));

            return Response.ok(new GenericEntity<List<ExamDto>>(examsAverageDtos){}).build();
        }

        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.getResolvedExams(userId, courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }

    @GET
    @Path("/exams/unsolved")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getUnresolvedExams(@PathParam("courseId") Long courseId){
        Long userId = authFacade.getCurrentUserId();

        if(courseService.isPrivileged(userId, courseId) || !courseService.belongs(userId, courseId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.getUnresolvedExams(userId, courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }

    @GET
    @Path("/exams/answers")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCourseAnswers(@PathParam("courseId")Long courseId){
        Long userId = authFacade.getCurrentUserId();

        if(courseService.isPrivileged(userId, courseId)){
            return Response.status(Response.Status.FORBIDDEN).build(); // TODO: Ver si corresponse mandarle algo en este caso
        }

        List<AnswerDto> answers = answerService.getMarks(userId, courseId).stream().map(answer->AnswerDto.fromAnswer(uriInfo, answer)).collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<AnswerDto>>(answers){}).build();
    }

    @GET
    @Path("/exams/average")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getCourseAverage(@PathParam("courseId") Long courseId){
        Long userId = authFacade.getCurrentUserId();


        if(courseService.isPrivileged(userId, courseId)){
           return Response.status(Response.Status.FORBIDDEN).build(); // TODO: Ver si en este caso corresponse hacer algo
        }

        return Response.ok(new GenericEntity<Double>(answerService.getAverageOfUserInCourse(userId, courseId)){}).build();
    }





}
