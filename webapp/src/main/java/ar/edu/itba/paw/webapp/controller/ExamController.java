package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.ExamService;
import ar.edu.itba.paw.webapp.dto.ExamDto;
import ar.edu.itba.paw.webapp.security.model.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Path("exams")
public class ExamController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ExamService examService;



    @GET
    @Path("/unresolved/{courseId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getUnresolvedExams(@PathParam("courseId") Long courseId){
        Object userPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CampusUser user = (CampusUser) userPrincipal;

        if(courseService.isPrivileged(user.getUserId(), courseId) || !courseService.belongs(user.getUserId(), courseId)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.getUnresolvedExams(user.getUserId(), courseId).stream().map(ExamDto::fromExam).collect(Collectors.toList())){}).build();
    }

    @GET
    @Path("/resolved/{courseId}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getResolvedExams(@PathParam("courseId") Long courseId){
        Object userPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CampusUser user = (CampusUser) userPrincipal;

        if(courseService.isPrivileged(user.getUserId(), courseId) || !courseService.belongs(user.getUserId(), courseId)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.getResolvedExams(user.getUserId(), courseId).stream().map(ExamDto::fromExam).collect(Collectors.toList())){}).build();
    }


    @GET
    @Path("/{courseId}")
    @Produces(value={MediaType.APPLICATION_JSON,})
    public Response getExams(@PathParam("courseId") Long courseId){
        return  Response.ok(new GenericEntity<List<ExamDto>>(examService.listByCourse(courseId).stream().map(ExamDto::fromExam).collect(Collectors.toList())){}).build();
    }
}
