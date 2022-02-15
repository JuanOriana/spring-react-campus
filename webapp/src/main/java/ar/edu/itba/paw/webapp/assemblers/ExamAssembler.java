package ar.edu.itba.paw.webapp.assemblers;

import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.webapp.controllers.ExamController;
import ar.edu.itba.paw.webapp.dtos.course.CourseDto;
import ar.edu.itba.paw.webapp.dtos.exam.ExamDto;
import ar.edu.itba.paw.webapp.dtos.file.FileModelDto;
import ar.edu.itba.paw.webapp.mappers.ExamMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExamAssembler {

    @Autowired
    private CourseAssembler courseAssembler;

    @Autowired
    private FileModelAssembler fileModelAssembler;

    private static final ExamMapper mapper = Mappers.getMapper(ExamMapper.class);

    public ExamAssembler() {
        // For spring
    }

    public ExamDto toResource(Exam entity, boolean showDeepLinks) {
        ExamDto result = mapper.examToExamDto(entity);
        CourseDto courseDto = courseAssembler.toResource(entity.getCourse(), showDeepLinks);
        FileModelDto fileModelDto = fileModelAssembler.toResource(entity.getExamFile(), showDeepLinks);
        result.setCourse(courseDto);
        result.setExamFile(fileModelDto);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(ExamController.class).slash(entity.getExamId()).withSelfRel());
        result.setLinks(links);
        return result;
    }

    public List<ExamDto> toResources(List<Exam> exams, boolean showDeepLinks) {
        List<ExamDto> examDtoList = new ArrayList<>();
        exams.forEach(e -> examDtoList.add(toResource(e, showDeepLinks)));
        return examDtoList;
    }
}
