package ar.edu.itba.paw.models;

public class FileCategory {

    private long categoryId;
    private long fileId;
    private String categoryName;

    public FileCategory() {
    }

    public FileCategory(long categoryId, long fileId, String categoryName) {
        this.categoryId = categoryId;
        this.fileId = fileId;
        this.categoryName = categoryName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
