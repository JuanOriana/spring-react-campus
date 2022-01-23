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
    @Path("/unresolved/{courseId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getUnresolvedExams(@PathParam("courseId") Long courseId){
        Long userId = authFacade.getCurrentUserId();

        if(courseService.isPrivileged(userId, courseId) || !courseService.belongs(userId, courseId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.getUnresolvedExams(userId, courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }

    @GET
    @Path("/resolved/{courseId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getResolvedExams(@PathParam("courseId") Long courseId){
       Long userId = authFacade.getCurrentUserId();

        if(courseService.isPrivileged(userId, courseId) || !courseService.belongs(userId, courseId)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.getResolvedExams(userId, courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }




    @GET
    @Path("/{courseId}")
    @Produces(value={MediaType.APPLICATION_JSON,})
    public Response getExams(@PathParam("courseId") Long courseId){
        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.listByCourse(courseId).stream().map(exam -> ExamDto.fromExam(uriInfo, exam,examService.getAverageScoreOfExam(exam.getExamId()))).collect(Collectors.toList())){}).build();
    }

    @GET
    @Path("/{courseId}/{examId}")
    @Produces(value={MediaType.APPLICATION_JSON,})
    public Response getExams(@PathParam("courseId") Long courseId,@PathParam("examId")Long examId){
        ExamDto exam = ExamDto.fromExam(uriInfo,examService.findById(examId).orElseThrow(ExamNotFoundException::new), examService.getAverageScoreOfExam(examId));

        return  Response.ok(exam).build();
    }

    @POST
    @Path("/{courseId}")
    @Consumes(value={MediaType.APPLICATION_JSON,})
    @Produces(value ={MediaType.APPLICATION_JSON,})
    public Response newExam(@Valid ExamFormDto examFormDto, @PathParam("courseId")Long courseId){
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
    @Path("/{courseId}/{examId}")
    public Response deleteExam(@PathParam("courseId")Long courseId,@PathParam("examId") Long examId){
        Long userId = authFacade.getCurrentUserId();

        if(courseService.isPrivileged(userId, courseId)){
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
