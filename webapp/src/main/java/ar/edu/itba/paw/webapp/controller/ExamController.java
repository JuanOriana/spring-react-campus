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
    private AuthFacade authFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

    @GET
    @Path("/{examId}")
    @Produces(value={MediaType.APPLICATION_JSON,})
    public Response getExams(@PathParam("examId")Long examId){
        ExamDto exam = ExamDto.fromExam(uriInfo,examService.findById(examId).orElseThrow(ExamNotFoundException::new), examService.getAverageScoreOfExam(examId));
        return  Response.ok(exam).build();
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
