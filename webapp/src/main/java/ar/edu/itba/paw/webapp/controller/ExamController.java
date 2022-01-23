package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.ExamDto;
import ar.edu.itba.paw.webapp.dto.ExamFormDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Path("exams")
public class ExamController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ExamService examService;

    @Autowired
    private DtoConstraintValidator dtoValidator;

    @Autowired
    private AuthFacade authFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

    @GET
    @Path("/unsolved/{courseId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getUnresolvedExams(@PathParam("courseId") Long courseId){
        Long userId = authFacade.getCurrentUserId();

        if(courseService.isPrivileged(userId, courseId) || !courseService.belongs(userId, courseId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.getUnresolvedExams(userId, courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }

    @GET
    @Path("/solved/{courseId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getResolvedExams(@PathParam("courseId") Long courseId){
       Long userId = authFacade.getCurrentUserId();

        if(courseService.isPrivileged(userId, courseId) || !courseService.belongs(userId, courseId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.getResolvedExams(userId, courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }




    @GET
    @Produces(value={MediaType.APPLICATION_JSON,})
    public Response getCourseExams(@QueryParam("courseId") Long courseId){
        if(courseId==null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.listByCourse(courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }

    @GET
    @Path("/{examId}")
    @Produces(value={MediaType.APPLICATION_JSON,})
    public Response getExams(@PathParam("examId")Long examId){
        ExamDto exam = ExamDto.fromExam(uriInfo,examService.findById(examId).orElseThrow(ExamNotFoundException::new), examService.getAverageScoreOfExam(examId));
        return  Response.ok(exam).build();
    }

    @POST
    @Consumes(value={MediaType.APPLICATION_JSON,})
    @Produces(value ={MediaType.APPLICATION_JSON,})
    public Response newExam(@Valid ExamFormDto examFormDto,@QueryParam("courseId") Long courseId){ //TODO: Revisar si esta forma de pasar el courseId es correcta

        if(courseId==null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
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


    @DELETE
    @Path("/{examId}")
    public Response deleteExam(@PathParam("examId") Long examId){
        Long userId = authFacade.getCurrentUserId();
        Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);

        if(courseService.isPrivileged(userId, exam.getCourse().getCourseId())){
            if(examService.delete(examId)){
                LOGGER.debug("User with id {} deleted exam with id {}",userId,examId);
                return Response.ok().build();
            }else{
                return Response.noContent().build();
            }
        }

        return Response.status(Response.Status.FORBIDDEN).build();
    }

}
