package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.webapp.dtos.exam.ExamDto;
import org.mapstruct.Mapper;

@Mapper
public interface ExamMapper {
    ExamDto examToExamDto(Exam exam);
}
