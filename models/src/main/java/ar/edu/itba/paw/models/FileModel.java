package ar.edu.itba.paw.models;

import java.util.Date;
import java.io.File;

public class FileModel {

    private long fileId, size;
    private FileExtensionModel fileExtension;
    private String name;
    private Date date;
    private byte[] file;
    private FileCategory fileCategory;

    public FileModel() {
    }

    public FileModel(long fileId, long size, FileCategory fileCategory, String name, Date date, byte[] file, FileExtensionModel fileExtension) {
        this.fileId = fileId;
        this.size = size;
        this.fileCategory = fileCategory;
        this.name = name;
        this.date = date;
        this.file = file;
        this.fileExtension = fileExtension;
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

    public FileCategory getCategory() {
        return fileCategory;
    }

    public void setCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
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

    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }
}

