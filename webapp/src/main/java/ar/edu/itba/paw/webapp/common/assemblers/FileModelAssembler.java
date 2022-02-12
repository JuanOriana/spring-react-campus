package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.FileModel;
import ar.edu.itba.paw.webapp.controllers.FileController;
import ar.edu.itba.paw.webapp.dto.course.CourseDto;
import ar.edu.itba.paw.webapp.dto.file.FileCategoryDto;
import ar.edu.itba.paw.webapp.dto.file.FileExtensionDto;
import ar.edu.itba.paw.webapp.dto.file.FileModelDto;
import ar.edu.itba.paw.webapp.common.mappers.FileModelMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileModelAssembler {

    private static final FileModelMapper mapper = Mappers.getMapper(FileModelMapper.class);

    @Autowired
    private FileCategoryAssembler fileCategoryAssembler;

    @Autowired
    private FileExtensionAssembler fileExtensionAssembler;

    @Autowired
    private CourseAssembler courseAssembler;

    public FileModelAssembler() {
        // For spring
    }

    public FileModelDto toResource(FileModel entity, boolean showDeepLinks) {
        if(entity == null) return new FileModelDto();
        FileModelDto result = mapper.fileModelToFileModelDto(entity);
        FileCategoryDto fileCategoryDto = fileCategoryAssembler.toResource(entity.getCategories().get(0));
        FileExtensionDto fileExtensionDto = fileExtensionAssembler.toResource(entity.getExtension());
        CourseDto courseDto = courseAssembler.toResource(entity.getCourse(), showDeepLinks);
        result.setCourse(courseDto);
        result.setFileCategory(fileCategoryDto);
        result.setExtension(fileExtensionDto);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(FileController.class).slash(entity.getFileId()).withSelfRel());
        result.setLinks(links);
        return result;
    }

    public List<FileModelDto> toResources(List<FileModel> fileModels, boolean showDeepLinks) {
        List<FileModelDto> fileModelDtoList = new ArrayList<>();
        fileModels.forEach(f -> fileModelDtoList.add(toResource(f,  showDeepLinks)));
        return fileModelDtoList;
    }
}
