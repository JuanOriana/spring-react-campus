package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exception.CourseNotFoundException;
import ar.edu.itba.paw.webapp.constraint.validator.DtoConstraintValidator;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.security.api.exception.DtoValidationException;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Path("admin")
@Component
@ComponentScan({"ar.edu.itba.paw.webapp.constraint.validator"})
public class AdminControllerREST {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private AuthFacade authFacade;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    //TODO: pasar cada metodo a su controller correspondiente


    @GET
    @Path("/course/{courseId}/edit")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response editCourse(@PathParam("courseId") Long courseId) throws DtoValidationException {

        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (courseId != null){
            return Response.ok(CourseFormDto.fromCourse(courseService.findById(courseId).orElseThrow(CourseNotFoundException::new), timetableService.getStartTimesOf(courseId), timetableService.getEndTimesOf(courseId))).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/course/{courseId}/edit")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response editCourse(@PathParam("courseId") Long courseId, @Valid CourseFormDto courseForm) throws DtoValidationException{ //TODO: startTimes y endTimes
        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(courseForm != null) {
            if (courseService.update(courseId, courseForm.getYear(), courseForm.getQuarter(), courseForm.getBoard(), courseForm.getSubjectId(), courseForm.getStartTimes(), courseForm.getEndTimes())){
                LOGGER.debug("Updated course in year {} in quarter {} of subjectId {} with id {}", courseForm.getYear(),
                        courseForm.getBoard(), courseForm.getSubjectId(), courseId);
                return Response.ok(Response.Status.ACCEPTED).build(); //TODO: ver si retornar algo mas
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("/course/{courseId}/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@PathParam("courseId") Long courseId) throws DtoValidationException{

        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (courseId != null){
            List<User> unenrolledUsers = courseService.listUnenrolledUsers(courseId);

            if (unenrolledUsers.isEmpty()){
                return Response.ok().status(Response.Status.NO_CONTENT).build();
            }

            List<Role> roles = roleService.list(); //TODO: hacer un endpoint separado?
            return Response.ok( EnrollUserToCourseResponseDto.fromUserToCourseInformation(unenrolledUsers, roles) ).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/course/{courseId}/enroll")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response addUserToCourse(@Valid UserToCourseFormDto userToCourseForm, @PathParam("courseId") Long courseId) throws DtoValidationException, URISyntaxException {

        if (!authFacade.isAdminUser()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (userToCourseForm != null){
            courseService.enroll(userToCourseForm.getUserId(), courseId, userToCourseForm.getRoleId());
            LOGGER.debug("User {} successfully enrolled in {}", userToCourseForm.getUserId(), courseId);
            URI courseUri = new URI(uriInfo.getBaseUri().normalize()+ "admin/course/" + courseId); //TODO: rev si termina quedando asi
            return Response.seeOther(courseUri).status(Response.Status.SEE_OTHER).build(); //TODO: ver si esta bien esta redireccion

        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
