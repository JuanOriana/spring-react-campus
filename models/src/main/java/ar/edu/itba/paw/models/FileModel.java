package ar.edu.itba.paw.models;

import java.util.Date;
import java.io.File;

public class FileModel {

    private long fileId, size, categoryId;
    private String name;
    private Date date;
    private File file;

    public FileModel() {
    }

    public FileModel(long fileId, long size, long categoryId, String name, Date date, File file) {
        this.fileId = fileId;
        this.size = size;
        this.categoryId = categoryId;
        this.name = name;
        this.date = date;
        this.file = file;
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
