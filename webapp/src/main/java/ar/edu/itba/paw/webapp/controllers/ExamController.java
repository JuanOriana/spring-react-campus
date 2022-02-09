package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import ar.edu.itba.paw.webapp.common.assemblers.AnswerAssembler;
import ar.edu.itba.paw.webapp.common.assemblers.ExamAssembler;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import ar.edu.itba.paw.webapp.dto.ExamDto;
import ar.edu.itba.paw.webapp.dto.SolveExamFormDto;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private AnswerAssembler answerAssembler;

    @Autowired
    private ExamAssembler examAssembler;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

    @GET
    @Path("/{examId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getExams(@PathParam("examId") Long examId) {
        Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);
        ExamDto examDto = examAssembler.toResource(exam);
        return Response.ok(new GenericEntity<ExamDto>(examDto){}).build();
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
                               @QueryParam("page-size") @DefaultValue("10") Integer pageSize) {
        Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);
        Long userId = authFacade.getCurrentUserId();
        if(courseService.isPrivileged(userId, exam.getCourse().getCourseId())) {
            CampusPage<Answer> paginatedAnswers = answerService.getFilteredAnswers(examId, filter, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<AnswerDto>>(
                            paginatedAnswers.getContent()
                                    .stream()
                                    .map(answer -> answerAssembler.toResource(answer))
                                    .collect(Collectors.toList())){});
            return PaginationBuilder.build(paginatedAnswers, builder, uriInfo, pageSize);
        }
        List<AnswerDto> answerDtoList = answerService.getMarks(userId, exam.getCourse().getCourseId())
                .stream()
                .map(answer -> answerAssembler.toResource(answer))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<AnswerDto>>(answerDtoList){}).build();
    }

    @POST
    @Path("/{examId}/answers")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/vnd.campus.api.v1+json")
    public Response postAnswer(@PathParam("examId") Long examId,
                               @FormDataParam("file") InputStream fileStream,
                               @FormDataParam("file") FormDataContentDisposition fileMetadata) throws IOException {
        if (examId == null) {
            throw new BadRequestException();
        }
        Exam exam = examService.findById(examId).orElseThrow(ExamNotFoundException::new);
        File file = getFileFromStream(fileStream);
        if (file.length() == 0) throw new BadRequestException("No file was provided");
        Answer answer = answerService.updateEmptyAnswer(exam.getExamId(),
                authFacade.getCurrentUser(),
                fileMetadata.getFileName(),
                IOUtils.toByteArray(fileStream),
                file.length(),
                LocalDateTime.now());
        URI location = URI.create(uriInfo.getBaseUri() + "/answers/" + answer.getAnswerId());
        return Response.created(location).build();
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

}
