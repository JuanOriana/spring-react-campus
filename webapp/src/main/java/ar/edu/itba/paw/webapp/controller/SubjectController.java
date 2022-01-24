package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import ar.edu.itba.paw.webapp.dto.SubjectFormDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Path("subjects")
public class SubjectController {


    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private SubjectService subjectService;


    @Autowired
    private DtoConstraintValidator dtoValidator;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getSubjects(){

        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<Subject> subjects = subjectService.list();


        if (subjects.isEmpty()){
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }

        return Response.ok( new GenericEntity<List<SubjectDto>>(subjects.stream().map(SubjectDto::fromSubject).collect(Collectors.toList())){}).build();
    }

    @POST
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getSubject(@Valid SubjectFormDto subjectFormDto){

        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Subject subject = subjectService.create(subjectFormDto.getCode(),subjectFormDto.getName());

        return Response.ok(SubjectDto.fromSubject(subject)).build();
    }
}
