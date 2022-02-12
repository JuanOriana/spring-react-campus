package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.webapp.controllers.FileController;
import ar.edu.itba.paw.webapp.dto.file.FileExtensionDto;
import ar.edu.itba.paw.webapp.common.mappers.FileExtensionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileExtensionAssembler {

    private static final FileExtensionMapper mapper = Mappers.getMapper(FileExtensionMapper.class);

    public FileExtensionAssembler() {
        // For spring
    }

    public FileExtensionDto toResource(FileExtension entity) {
        FileExtensionDto result = mapper.fileExtensionToFileExtensionDto(entity);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(FileController.class).slash(entity.getFileExtensionId()).withSelfRel());
        result.setLinks(links);
        return result;
    }

    public List<FileExtensionDto> toResources(List<FileExtension> fileCategories) {
        List<FileExtensionDto> fileExtensionDtos = new ArrayList<>();
        fileCategories.forEach(f -> fileExtensionDtos.add(toResource(f)));
        return fileExtensionDtos;
    }
}
