package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.AnswerService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.models.exception.AnswerNotFoundException;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("answers")
@Component
@ComponentScan({"ar.edu.itba.paw.webapp.constraint.validator"})
public class AnswerController {

    @Autowired
    private UriInfo uriInfo;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthFacade authFacade;


    @GET
    @Path("{answerId}")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getAnswer(@PathParam("answerId")Long answerId){
        Answer answer = answerService.findById(answerId).orElseThrow(AnswerNotFoundException::new);
        Long userId = authFacade.getCurrentUserId();

        if(answer.getStudent().getUserId().equals(userId) || courseService.isPrivileged(userId, answer.getExam().getCourse().getCourseId())){
            return Response.ok(AnswerDto.fromAnswer(uriInfo,answer)).build();
        }

        return Response.status(Response.Status.FORBIDDEN).build();
    }


    @DELETE
    @Path("{answerId}")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response deleteAnswer(@PathParam("answerId")Long answerId){
        Answer answer = answerService.findById(answerId).orElseThrow(AnswerNotFoundException::new);
        Long userId = authFacade.getCurrentUserId();

        if(answer.getStudent().getUserId().equals(userId) || courseService.isPrivileged(userId, answer.getExam().getCourse().getCourseId())){
            if(answerService.delete(answerId)) {
                return Response.ok().build();
            }else{
                return Response.noContent().build();
            }
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
