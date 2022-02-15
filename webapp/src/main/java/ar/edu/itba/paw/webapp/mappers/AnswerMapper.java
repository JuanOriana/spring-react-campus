package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.webapp.dtos.answer.AnswerDto;
import org.mapstruct.Mapper;

@Mapper
public interface AnswerMapper {
    AnswerDto answerToAnswerDto(Answer answer);
    Answer answerDtoToAnswer(AnswerDto answerDto);
}
