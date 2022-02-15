package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.webapp.dtos.course.CourseDto;
import ar.edu.itba.paw.webapp.dtos.subject.SubjectDto;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper {
    CourseDto courseToCourseDto(Course course);
    SubjectDto subjectToSubjectDto(Subject subject);
}
