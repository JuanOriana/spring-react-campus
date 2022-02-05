package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.exception.AnswerNotFoundException;
import ar.edu.itba.paw.webapp.assembler.AnswerAssembler;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
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
    private AnswerAssembler answerAssembler;

    @GET
    @Path("/{answerId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getAnswer(@PathParam("answerId") Long answerId) {
        Answer answer = answerService.findById(answerId).orElseThrow(AnswerNotFoundException::new);
        return Response.ok(new GenericEntity<AnswerDto>(answerAssembler.toResource(answer)){}).build();
    }


    @DELETE
    @Path("/{answerId}")
    @Produces("application/vnd.campus.api.v1+json")
    public Response deleteAnswer(@PathParam("answerId") Long answerId) {
        if(!answerService.delete(answerId)) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }
}
