package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.models.exception.ExamNotFoundException;
import ar.edu.itba.paw.webapp.assemblers.AnswerAssembler;
import ar.edu.itba.paw.webapp.assemblers.ExamAssembler;
import ar.edu.itba.paw.webapp.dtos.answer.AnswerDto;
import ar.edu.itba.paw.webapp.dtos.exam.ExamDto;
import ar.edu.itba.paw.webapp.security.api.exceptions.CampusBadRequestException;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.utils.PaginationBuilder;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Path("/api/exams")
public class ExamController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ExamService examService;

    @Autowired
    private AuthFacade authFacade;

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
        ExamDto examDto = examAssembler.toResource(exam, false);
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
                               @QueryParam("filter-by") @DefaultValue("") String filter,
                               @QueryParam("page") @DefaultValue("1") Integer page,
                               @QueryParam("page-size") @DefaultValue("10") Integer pageSize) {
        CampusPage<Answer> paginatedAnswers = answerService.getFilteredAnswers(examId, filter, page, pageSize);
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<AnswerDto>>(
                        paginatedAnswers.getContent()
                                .stream()
                                .map(answer -> answerAssembler.toResource(answer, false))
                                .collect(Collectors.toList())){});
        return PaginationBuilder.build(paginatedAnswers, builder, uriInfo, pageSize);
    }

    @PUT
    @Path("/{examId}/answers")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/vnd.campus.api.v1+json")
    public Response uploadAnswer(@PathParam("examId") Long examId,
                                 @FormDataParam("file") InputStream fileStream,
                                 @FormDataParam("file") FormDataContentDisposition fileMetadata) throws IOException {
        File file = getFileFromStream(fileStream);
        if (file.length() == 0) throw new CampusBadRequestException("No file or file metadata was provided");
        Answer answer = answerService.updateEmptyAnswer(examId, authFacade.getCurrentUserId(), fileMetadata.getFileName(),
                IOUtils.toByteArray(fileStream), file.length());
        LOGGER.debug("Uploaded answer file {} to exam {}", answer.getAnswerFile().getFileName(), answer.getExam().getExamId());
        return Response.ok().build();
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
