package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.webapp.controllers.FileController;
import ar.edu.itba.paw.webapp.dto.FileExtensionDto;
import ar.edu.itba.paw.webapp.common.mappers.FileExtensionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileExtensionAssembler extends JaxRsResourceAssemblerSupport<FileExtension, FileExtensionDto> {

    private static final FileExtensionMapper mapper = Mappers.getMapper(FileExtensionMapper.class);

    public FileExtensionAssembler() {
        super(FileController.class, FileExtensionDto.class);
    }

    @Override
    public FileExtensionDto toResource(FileExtension entity) {
        FileExtensionDto fileExtensionDto = createResourceWithId(entity.getFileExtensionId(), entity);
        FileExtensionDto result = mapper.fileExtensionToFileExtensionDto(entity);
        result.add(JaxRsLinkBuilder.linkTo(FileController.class).slash("extensions").slash(entity.getFileExtensionId()).withSelfRel());
        return result;
    }
}
