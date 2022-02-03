package ar.edu.itba.paw.webapp.assembler;

import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.webapp.controller.SubjectController;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import ar.edu.itba.paw.webapp.mapper.CourseMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class SubjectAssembler extends JaxRsResourceAssemblerSupport<Subject, SubjectDto> {

    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    public SubjectAssembler() {
        super(SubjectController.class, SubjectDto.class);
    }

    @Override
    public SubjectDto toResource(Subject entity) {
        SubjectDto subject = createResourceWithId(entity.getSubjectId(), entity);
        SubjectDto result = mapper.subjectToSubjectDto(entity);
        result.add(subject.getLinks());
        return result;
    }
}
