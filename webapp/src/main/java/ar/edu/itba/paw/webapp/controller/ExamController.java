package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import ar.edu.itba.paw.webapp.dto.ExamDto;
import ar.edu.itba.paw.webapp.dto.SolveExamFormDto;
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
    private AnswerService answerService;

    @Autowired
    private ExamService examService;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private ResponsePaging<Answer> answerResponsePaging;

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

    @GET
    @Path("/{examId}/answers")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getAnswers(@PathParam("examId")Long examId,@QueryParam("filterBy")@DefaultValue("corrected")String filter,@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("pageSize") @DefaultValue("10") Integer pageSize){
        Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);

        if(courseService.isPrivileged(authFacade.getCurrentUserId(),exam.getCourse().getCourseId())){
            CampusPage<Answer> answersPaginated = answerService.getFilteredAnswers(examId, filter, page, pageSize);

            Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AnswerDto>>(answersPaginated.getContent().stream().map(answer-> AnswerDto.fromAnswer(uriInfo, answer)).collect(Collectors.toList())){});

            answerResponsePaging.paging(answersPaginated, response, uriInfo, pageSize);

            return response.build();
        }

        List<AnswerDto> answerDtos = answerService.getMarks(authFacade.getCurrentUserId(), exam.getCourse().getCourseId()).stream().map(answer -> AnswerDto.fromAnswer(uriInfo, answer)).collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<AnswerDto>>(answerDtos){}).build();
    }


    @POST
    @Path("/{examId}/answers")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response newAnswer(@PathParam("examId") Long examId, @Valid SolveExamFormDto solveExamFormDto){

        Long userId = authFacade.getCurrentUserId();
        Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);

        if(courseService.isPrivileged(userId,exam.getCourse().getCourseId())){
            return Response.status(Response.Status.FORBIDDEN).build(); // Un profesor/ayudante no deberia postear un answer
        }

        Answer answer = answerService.updateEmptyAnswer(examId, authFacade.getCurrentUser(), solveExamFormDto.getExam().getName(), solveExamFormDto.getExam().getBytes(), solveExamFormDto.getExam().getSize(), LocalDateTime.now());

        return Response.ok(AnswerDto.fromAnswer(uriInfo, answer)).build();
    }

}
