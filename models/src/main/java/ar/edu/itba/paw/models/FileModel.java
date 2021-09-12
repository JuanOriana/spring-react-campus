package ar.edu.itba.paw.models;

import java.util.Date;
import java.util.Objects;

public class FileModel {

    private long fileId, size;
    private FileExtensionModel fileExtension;
    private String name;
    private Date date;
    private byte[] file;
    private Course course;

    public FileModel() {
    }

    public FileModel(long fileId, long size, String name, Date date, byte[] file, FileExtensionModel fileExtension, Course course) {
        this.fileId = fileId;
        this.size = size;
        this.name = name;
        this.date = date;
        this.file = file;
        this.fileExtension = fileExtension;
        this.course = course;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public FileExtensionModel getExtension() {
        return fileExtension;
    }

    public void setExtension(FileExtensionModel fileExtension) {
        this.fileExtension = fileExtension;
    }

    public FileExtensionModel getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtensionModel fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileModel fileModel = (FileModel) o;
        return fileId == fileModel.fileId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId);
    }
}

