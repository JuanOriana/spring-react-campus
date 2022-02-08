package ar.edu.itba.paw.webapp.common.mappers;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import org.mapstruct.Mapper;

@Mapper
public interface AnswerMapper {
    AnswerDto answerToAnswerDto(Answer answer);
}
