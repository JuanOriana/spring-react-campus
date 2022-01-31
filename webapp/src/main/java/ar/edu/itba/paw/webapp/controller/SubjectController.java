package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.SubjectService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.exception.SubjectNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import ar.edu.itba.paw.webapp.dto.SubjectFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Path("subjects")
public class SubjectController {

    @Context
    UriInfo uriInfo;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private DtoConstraintValidator dtoValidator;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getSubjects() {
        List<Subject> subjectList = subjectService.list();
        if (subjectList.isEmpty()){
            return Response.noContent().build();
        }
        List<SubjectDto> subjects = subjectList.stream().map(SubjectDto::fromSubject).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectDto>>(subjects){}).build();
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getSubject(@Valid SubjectFormDto subjectFormDto){
        dtoValidator.validate(subjectFormDto, "Invalid Body Request");
        Subject subject = subjectService.create(subjectFormDto.getCode(),subjectFormDto.getName());
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + subject.getSubjectId());
        return Response.created(location).build();
    }

    @GET
    @Path("/{subjectId}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getSubject(@PathParam("subjectId") Long subjectId) {
        Subject subject = subjectService.findById(subjectId).orElseThrow(SubjectNotFoundException::new);
        return Response.ok(SubjectDto.fromSubject(subject)).build();
    }
}
