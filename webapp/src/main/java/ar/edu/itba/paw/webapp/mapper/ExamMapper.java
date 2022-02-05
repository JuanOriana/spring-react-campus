package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.webapp.dto.ExamDto;
import org.mapstruct.Mapper;

@Mapper
public interface ExamMapper {
    ExamDto examToExamDto(Exam exam);
}
