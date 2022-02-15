package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.webapp.dtos.file.FileCategoryDto;
import org.mapstruct.Mapper;

@Mapper
public interface FileCategoryMapper {
    FileCategoryDto fileCategoryToFileCategoryDto(FileCategory fileCategory);
}
