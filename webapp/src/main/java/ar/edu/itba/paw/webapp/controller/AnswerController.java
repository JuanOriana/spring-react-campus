package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.exception.AnswerNotFoundException;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("answers")
@Component
public class AnswerController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthFacade authFacade;

    @GET
    @Path("/{answerId}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getAnswer(@PathParam("answerId") Long answerId) {
        Answer answer = answerService.findById(answerId).orElseThrow(AnswerNotFoundException::new);
        return Response.ok(AnswerDto.fromAnswer(uriInfo,answer)).build();
    }


    @DELETE
    @Path("/{answerId}")
    @Produces(value= MediaType.APPLICATION_JSON)
    public Response deleteAnswer(@PathParam("answerId") Long answerId) {
        if(!answerService.delete(answerId)) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }
}
