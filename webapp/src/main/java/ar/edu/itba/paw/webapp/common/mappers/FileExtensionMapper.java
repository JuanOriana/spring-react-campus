package ar.edu.itba.paw.webapp.common.mappers;

import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.webapp.dto.FileExtensionDto;
import org.mapstruct.Mapper;

@Mapper
public interface FileExtensionMapper {
    FileExtensionDto fileExtensionToFileExtensionDto(FileExtension fileExtension);
}
