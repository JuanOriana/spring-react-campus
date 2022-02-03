package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import ar.edu.itba.paw.webapp.dto.ExamDto;
import ar.edu.itba.paw.webapp.dto.SolveExamFormDto;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
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
    private DtoConstraintValidator dtoValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

    @GET
    @Path("/{examId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getExams(@PathParam("examId") Long examId) {
        ExamDto exam = ExamDto.fromExam(uriInfo, examService.findById(examId).orElseThrow(ExamNotFoundException::new), examService.getAverageScoreOfExam(examId));
        return Response.ok(exam).build();
    }

    @DELETE
    @Produces("application/vnd.campus.api.v1+json")
    @Path("/{examId}")
    public Response deleteExam(@PathParam("examId") Long examId) {
        Long userId = authFacade.getCurrentUserId();
        if(!examService.delete(examId)) {
            throw new NotFoundException();
        }
        LOGGER.debug("User with id {} deleted exam with id {}", userId, examId);
        return Response.noContent().build();
    }

    @GET
    @Path("/{examId}/answers")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getAnswers(@PathParam("examId") Long examId,
                               @QueryParam("filter-by") @DefaultValue("corrected") String filter,
                               @QueryParam("page") @DefaultValue("1") Integer page,
                               @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);
        Long userId = authFacade.getCurrentUserId();
        if(courseService.isPrivileged(userId, exam.getCourse().getCourseId())) {
            CampusPage<Answer> paginatedAnswers = answerService.getFilteredAnswers(examId, filter, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<AnswerDto>>(
                            paginatedAnswers.getContent()
                                    .stream()
                                    .map(answer-> AnswerDto.fromAnswer(uriInfo, answer))
                                    .collect(Collectors.toList())){});
            return PaginationBuilder.build(paginatedAnswers, builder, uriInfo, pageSize);
        }
        List<AnswerDto> answerDtoList = answerService.getMarks(userId, exam.getCourse().getCourseId())
                .stream()
                .map(answer -> AnswerDto.fromAnswer(uriInfo, answer))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<AnswerDto>>(answerDtoList){}).build();
    }

    @POST
    @Path("/{examId}/answers")
    @Produces("application/vnd.campus.api.v1+json")
    @Consumes("application/vnd.campus.api.v1+json")
    public Response newAnswer(@PathParam("examId") Long examId,
                              @Valid SolveExamFormDto solveExamFormDto)
            throws DtoValidationException {
        dtoValidator.validate(solveExamFormDto, "Invalid body request");
        Long userId = authFacade.getCurrentUserId();
        Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);
        if(!courseService.isPrivileged(userId, exam.getCourse().getCourseId())) {
            throw new ForbiddenException();
        }
        Answer answer = answerService.updateEmptyAnswer(examId, authFacade.getCurrentUser(),
                solveExamFormDto.getExam().getName(),
                solveExamFormDto.getExam().getBytes(),
                solveExamFormDto.getExam().getSize(),
                LocalDateTime.now());
        URI location = URI.create(uriInfo.getBaseUri() + "/answers/" + answer.getAnswerId());
        return Response.created(location).build();
    }

}
