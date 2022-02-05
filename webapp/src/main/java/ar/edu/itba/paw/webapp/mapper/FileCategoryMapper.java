package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.models.FileCategory;
import ar.edu.itba.paw.webapp.dto.FileCategoryDto;
import org.mapstruct.Mapper;

@Mapper
public interface FileCategoryMapper {
    FileCategoryDto fileCategoryToFileCategoryDto(FileCategory fileCategory);
}
