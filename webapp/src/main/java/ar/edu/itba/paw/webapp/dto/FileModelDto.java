package ar.edu.itba.paw.webapp.dto;

import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

public class FileModelDto extends ResourceSupport {

    private long fileId;
    private long size;
    private FileExtensionDto extension;
    private String fileName;
    private LocalDateTime fileDate;
    private CourseDto course;
    private long downloads;
    private Boolean hidden;
    private FileCategoryDto fileCategory;

    public FileCategoryDto getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategoryDto fileCategory) {
        this.fileCategory = fileCategory;
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
