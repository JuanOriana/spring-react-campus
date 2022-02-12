package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.controllers.CourseController;
import ar.edu.itba.paw.webapp.dto.course.CourseDto;
import ar.edu.itba.paw.webapp.dto.subject.SubjectDto;
import ar.edu.itba.paw.webapp.common.mappers.CourseMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseAssembler {

    @Autowired
    private SubjectAssembler subjectAssembler;

    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    public CourseAssembler() {
        // For spring
    }

    public CourseDto toResource(Course entity, boolean showDeepLinks) {
        CourseDto result = mapper.courseToCourseDto(entity);
        SubjectDto subject = subjectAssembler.toResource(entity.getSubject());
        result.setSubject(subject);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).withSelfRel());
        if(showDeepLinks) {
            Link fileLink = new Link(
                    new UriTemplate(
                            JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("files").toString(),
                            new TemplateVariables(new TemplateVariable("category-type,extension-type,query,order-property,order-direction,page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                    ), "files"
            );
            links.add(fileLink);
            Link announcementsLink = new Link(
                    new UriTemplate(
                            JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("announcements").toString(),
                            new TemplateVariables(new TemplateVariable("page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                    ), "announcements"
            );
            links.add(announcementsLink);
            links.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("teachers").withRel("teachers"));
            links.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("helpers").withRel("helpers"));
            Link studentsLink = new Link(
                    new UriTemplate(
                            JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("students").toString(),
                            new TemplateVariables(new TemplateVariable("page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                    ), "students"
            );
            links.add(studentsLink);
            links.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("exams").withRel("exams"));
        }

        result.setLinks(links);
        return result;
    }

    public List<CourseDto> toResources(List<Course> courses, boolean showDeepLinks) {
        List<CourseDto> courseDtoList = new ArrayList<>();
        courses.forEach(c -> courseDtoList.add(toResource(c, showDeepLinks)));
        return courseDtoList;
    }
}
