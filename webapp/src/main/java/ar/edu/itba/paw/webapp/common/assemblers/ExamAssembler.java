package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Exam;
import ar.edu.itba.paw.webapp.controllers.ExamController;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.dto.ExamDto;
import ar.edu.itba.paw.webapp.dto.FileModelDto;
import ar.edu.itba.paw.webapp.common.mappers.ExamMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamAssembler extends JaxRsResourceAssemblerSupport<Exam, ExamDto> {

    @Autowired
    private CourseAssembler courseAssembler;

    @Autowired
    private FileModelAssembler fileModelAssembler;

    private static final ExamMapper mapper = Mappers.getMapper(ExamMapper.class);

    public ExamAssembler() {
        super(ExamController.class, ExamDto.class);
    }

    @Override
    public ExamDto toResource(Exam entity) {
        ExamDto examDto = createResourceWithId(entity.getExamId(), entity);
        ExamDto result = mapper.examToExamDto(entity);
        CourseDto courseDto = courseAssembler.toResource(entity.getCourse());
        FileModelDto fileModelDto = fileModelAssembler.toResource(entity.getExamFile());
        result.setCourse(courseDto);
        result.setExamFile(fileModelDto);
        result.add(examDto.getLinks());
        return result;
    }
}
