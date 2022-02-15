package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.FileExtension;
import ar.edu.itba.paw.webapp.dtos.file.FileExtensionDto;
import org.mapstruct.Mapper;

@Mapper
public interface FileExtensionMapper {
    FileExtensionDto fileExtensionToFileExtensionDto(FileExtension fileExtension);
}
