package ar.edu.itba.paw.webapp.assembler;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.controller.CourseController;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import ar.edu.itba.paw.webapp.mapper.CourseMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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
        result.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("files").withRel("files"));
        result.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("teachers").withRel("teachers"));
        result.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("helpers").withRel("helpers"));
        result.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("students").withRel("students"));
        result.add(JaxRsLinkBuilder.linkTo(CourseController.class).slash(entity.getCourseId()).slash("exams").withRel("exams"));
        return result;
    }
}
