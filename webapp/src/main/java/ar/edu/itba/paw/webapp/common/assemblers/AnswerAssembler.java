package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Answer;
import ar.edu.itba.paw.webapp.controllers.AnnouncementsController;
import ar.edu.itba.paw.webapp.controllers.AnswerController;
import ar.edu.itba.paw.webapp.dto.AnswerDto;
import ar.edu.itba.paw.webapp.dto.FileModelDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.common.mappers.AnswerMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerAssembler {

    private static final AnswerMapper mapper = Mappers.getMapper(AnswerMapper.class);

    @Autowired
    private UserAssembler userAssembler;


    @Autowired
    private FileModelAssembler fileModelAssembler;

    public AnswerAssembler() {
        // For spring
    }

    public AnswerDto toResource(Answer entity, boolean showDeepLinks) {
        AnswerDto result = mapper.answerToAnswerDto(entity);
        UserDto student = userAssembler.toResource(entity.getStudent(), showDeepLinks);
        UserDto teacher = userAssembler.toResource(entity.getTeacher(), showDeepLinks);
        FileModelDto answerFile = fileModelAssembler.toResource(entity.getAnswerFile(), showDeepLinks);
        result.setAnswerFile(answerFile);
        result.setStudent(student);
        result.setTeacher(teacher);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(AnswerController.class).slash(entity.getAnswerId()).withSelfRel());
        result.setLinks(links);
        return result;
    }

    public List<AnswerDto> toResources(List<Answer> answers, boolean showDeepLinks) {
        List<AnswerDto> answerDtoList = new ArrayList<>();
        answers.forEach(a -> answerDtoList.add(toResource(a, showDeepLinks)));
        return answerDtoList;
    }
}
