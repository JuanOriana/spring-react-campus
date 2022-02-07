package ar.edu.itba.paw.webapp.assembler;

import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.webapp.controller.FileController;
import ar.edu.itba.paw.webapp.dto.FileCategoryDto;
import ar.edu.itba.paw.webapp.mapper.FileCategoryMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileCategoryAssembler extends JaxRsResourceAssemblerSupport<FileCategory, FileCategoryDto> {

    private static final FileCategoryMapper mapper = Mappers.getMapper(FileCategoryMapper.class);

    public FileCategoryAssembler() {
        super(FileController.class, FileCategoryDto.class);
    }

    @Override
    public FileCategoryDto toResource(FileCategory entity) {
        FileCategoryDto fileCategoryDto = createResourceWithId(entity.getCategoryId(), entity);
        FileCategoryDto result = mapper.fileCategoryToFileCategoryDto(entity);
        result.add(JaxRsLinkBuilder.linkTo(FileController.class).slash("categories").slash(entity.getCategoryId()).withSelfRel());
        return result;
    }
}
