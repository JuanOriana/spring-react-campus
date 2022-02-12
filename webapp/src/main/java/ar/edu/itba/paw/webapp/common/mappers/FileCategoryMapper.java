package ar.edu.itba.paw.webapp.common.mappers;

import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.webapp.dto.file.FileCategoryDto;
import org.mapstruct.Mapper;

@Mapper
public interface FileCategoryMapper {
    FileCategoryDto fileCategoryToFileCategoryDto(FileCategory fileCategory);
}
