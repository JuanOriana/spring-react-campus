package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.webapp.controllers.AnswerController;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import ar.edu.itba.paw.webapp.dto.FileModelDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.common.mappers.AnswerMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnswerAssembler extends JaxRsResourceAssemblerSupport<Answer, AnswerDto> {

    private static final AnswerMapper mapper = Mappers.getMapper(AnswerMapper.class);

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private FileModelAssembler fileModelAssembler;

    public AnswerAssembler() {
        super(AnswerController.class, AnswerDto.class);
    }

    @Override
    public AnswerDto toResource(Answer entity) {
        AnswerDto answerDto = createResourceWithId(entity.getAnswerId(), entity);
        AnswerDto result = mapper.answerToAnswerDto(entity);
        UserDto student = userAssembler.toResource(entity.getStudent());
        UserDto teacher = userAssembler.toResource(entity.getTeacher());
        FileModelDto answerFile = fileModelAssembler.toResource(entity.getAnswerFile());
        result.setAnswerFile(answerFile);
        result.setStudent(student);
        result.setTeacher(teacher);
        result.add(answerDto.getLinks());
        return result;
    }
}
