package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.webapp.dtos.file.FileModelDto;
import org.mapstruct.Mapper;

@Mapper
public interface FileModelMapper {
    FileModelDto fileModelToFileModelDto(FileModel fileModel);
}
