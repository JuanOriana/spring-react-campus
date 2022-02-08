package ar.edu.itba.paw.webapp.common.mappers;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper {
    CourseDto courseToCourseDto(Course course);
    SubjectDto subjectToSubjectDto(Subject subject);
}
