package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.webapp.dto.FileModelDto;
import org.mapstruct.Mapper;

@Mapper
public interface FileModelMapper {
    FileModelDto fileModelToFileModelDto(FileModel fileModel);
}
