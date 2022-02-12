package ar.edu.itba.paw.webapp.common.mappers;

import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.webapp.dto.file.FileModelDto;
import org.mapstruct.Mapper;

@Mapper
public interface FileModelMapper {
    FileModelDto fileModelToFileModelDto(FileModel fileModel);
}
