package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.controllers.CourseController;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import ar.edu.itba.paw.webapp.common.mappers.CourseMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CourseAssembler extends JaxRsResourceAssemblerSupport<Course, CourseDto> {

    @Autowired
    private SubjectAssembler subjectAssembler;

    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    public CourseAssembler() {
        super(CourseController.class, CourseDto.class);
    }

    @Override
    public CourseDto toResource(Course entity) {
        CourseDto course = createResourceWithId(entity.getCourseId(), entity);
        CourseDto result = mapper.courseToCourseDto(entity);
        SubjectDto subject = subjectAssembler.toResource(entity.getSubject());
        result.setSubject(subject);
        result.add(course.getLinks());
        Link fileLink = new Link(
                new UriTemplate(
                        JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("files").toString(),
                        new TemplateVariables(new TemplateVariable("category-type,extension-type,query,order-property,order-direction,page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                ), "files"
        );
        result.add(fileLink);
        Link announcementsLink = new Link(
                new UriTemplate(
                        JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("announcements").toString(),
                        new TemplateVariables(new TemplateVariable("page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                ), "announcements"
        );
        result.add(announcementsLink);
        result.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("teachers").withRel("teachers"));
        result.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("helpers").withRel("helpers"));
        Link studentsLink = new Link(
                new UriTemplate(
                        JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("students").toString(),
                        new TemplateVariables(new TemplateVariable("page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                ), "students"
        );
        result.add(studentsLink);
        result.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("exams").withRel("exams"));
        return result;
    }
}
