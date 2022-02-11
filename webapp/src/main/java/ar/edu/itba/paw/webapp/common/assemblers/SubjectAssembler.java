package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.webapp.controllers.SubjectController;
import ar.edu.itba.paw.webapp.controllers.UserController;
import ar.edu.itba.paw.webapp.dto.SubjectDto;
import ar.edu.itba.paw.webapp.common.mappers.CourseMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectAssembler {

    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    public SubjectAssembler() {
        // For spring
    }


    public SubjectDto toResource(Subject entity) {
        SubjectDto result = mapper.subjectToSubjectDto(entity);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(SubjectController.class).slash(entity.getSubjectId()).withSelfRel());
        result.setLinks(links);
        return result;
    }

    public List<SubjectDto> toResources(List<Subject> subjects) {
        List<SubjectDto> subjectDtos = new ArrayList<>();
        subjects.forEach(s -> subjectDtos.add(toResource(s)));
        return subjectDtos;
    }
}
