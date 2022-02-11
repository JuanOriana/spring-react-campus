package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.webapp.controllers.FileController;
import ar.edu.itba.paw.webapp.dto.FileCategoryDto;
import ar.edu.itba.paw.webapp.common.mappers.FileCategoryMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileCategoryAssembler {

    private static final FileCategoryMapper mapper = Mappers.getMapper(FileCategoryMapper.class);

    public FileCategoryAssembler() {
        // For spring
    }

    public FileCategoryDto toResource(FileCategory entity) {
        FileCategoryDto result = mapper.fileCategoryToFileCategoryDto(entity);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(FileController.class).slash("file-category").slash(entity.getCategoryId()).withSelfRel());
        result.setLinks(links);
        return result;
    }

    public List<FileCategoryDto> toResources(List<FileCategory> fileCategories) {
        List<FileCategoryDto> fileCategoryDtos = new ArrayList<>();
        fileCategories.forEach(f -> fileCategoryDtos.add(toResource(f)));
        return fileCategoryDtos;
    }
}
