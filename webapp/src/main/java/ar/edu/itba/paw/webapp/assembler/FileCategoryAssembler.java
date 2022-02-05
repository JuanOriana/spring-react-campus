package ar.edu.itba.paw.webapp.assembler;

import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.webapp.controller.FileCategoryController;
import ar.edu.itba.paw.webapp.dto.FileCategoryDto;
import ar.edu.itba.paw.webapp.mapper.FileCategoryMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class FileCategoryAssembler extends JaxRsResourceAssemblerSupport<FileCategory, FileCategoryDto> {

    private static final FileCategoryMapper mapper = Mappers.getMapper(FileCategoryMapper.class);

    public FileCategoryAssembler() {
        super(FileCategoryController.class, FileCategoryDto.class);
    }

    @Override
    public FileCategoryDto toResource(FileCategory entity) {
        FileCategoryDto fileCategoryDto = createResourceWithId(entity.getCategoryId(), entity);
        FileCategoryDto result = mapper.fileCategoryToFileCategoryDto(entity);
        result.add(fileCategoryDto.getLinks());
        return result;
    }
}
