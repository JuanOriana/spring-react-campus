package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.webapp.controllers.FileController;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.dto.FileCategoryDto;
import ar.edu.itba.paw.webapp.dto.FileExtensionDto;
import ar.edu.itba.paw.webapp.dto.FileModelDto;
import ar.edu.itba.paw.webapp.common.mappers.FileModelMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileModelAssembler extends JaxRsResourceAssemblerSupport<FileModel, FileModelDto> {

    private static final FileModelMapper mapper = Mappers.getMapper(FileModelMapper.class);

    @Autowired
    private FileCategoryAssembler fileCategoryAssembler;

    @Autowired
    private FileExtensionAssembler fileExtensionAssembler;

    @Autowired
    private CourseAssembler courseAssembler;

    public FileModelAssembler() {
        super(FileController.class, FileModelDto.class);
    }

    @Override
    public FileModelDto toResource(FileModel entity) {
        if(entity == null) return new FileModelDto();
        FileModelDto fileModelDto = createResourceWithId(entity.getFileId(), entity);
        FileModelDto result = mapper.fileModelToFileModelDto(entity);
        FileCategoryDto fileCategoryDto = fileCategoryAssembler.toResource(entity.getCategories().get(0));
        FileExtensionDto fileExtensionDto = fileExtensionAssembler.toResource(entity.getExtension());
        CourseDto courseDto = courseAssembler.toResource(entity.getCourse());
        result.setCourse(courseDto);
        result.setFileCategory(fileCategoryDto);
        result.setExtension(fileExtensionDto);
        result.add(fileModelDto.getLinks());
        return result;
    }
}
