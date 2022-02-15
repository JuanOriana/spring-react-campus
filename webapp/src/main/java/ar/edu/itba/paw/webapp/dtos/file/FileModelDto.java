package ar.edu.itba.paw.webapp.dtos.file;

import ar.edu.itba.paw.webapp.dtos.course.CourseDto;
import org.springframework.hateoas.Link;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class FileModelDto implements Serializable {

    private long fileId;
    private long size;
    private FileExtensionDto extension;
    private String fileName;
    private LocalDateTime date;
    private CourseDto course;
    private long downloads;
    private Boolean hidden;
    private FileCategoryDto fileCategory;
    private List<Link> links;

    public FileModelDto() {
        // For MessageBodyWriter
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
