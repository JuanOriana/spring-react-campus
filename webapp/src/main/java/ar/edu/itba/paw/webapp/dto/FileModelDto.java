package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FileModel;

import java.time.LocalDateTime;

public class FileModelDto {

    private long fileId;
    private long size;
    private FileExtensionDto extension;
    private String fileName;
    private LocalDateTime fileDate;
    private CourseDto course;
    private long downloads;
    private Boolean hidden;
    //TODO: ver si hace falta poner el FileCategory tmbn

    public static FileModelDto fromFile(FileModel file) {
        if (file == null) {
            return null;
        }

        final FileModelDto dto = new FileModelDto();
        dto.fileId = file.getFileId();
        dto.size = file.getSize();
        dto.extension = FileExtensionDto.fromFileExtension(file.getExtension());
        dto.fileName = file.getName();
        dto.fileDate = file.getDate();
        dto.course = CourseDto.fromCourse(file.getCourse());
        dto.downloads = file.getDownloads();
        dto.hidden = file.isHidden();
        return dto;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public FileExtensionDto getExtension() {
        return extension;
    }

    public void setExtension(FileExtensionDto extension) {
        this.extension = extension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getFileDate() {
        return fileDate;
    }

    public void setFileDate(LocalDateTime fileDate) {
        this.fileDate = fileDate;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public long getDownloads() {
        return downloads;
    }

    public void setDownloads(long downloads) {
        this.downloads = downloads;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
