package ar.edu.itba.paw.models;

public class FileExtension {

    private long fileExtensionId;
    private String fileExtension;

    public FileExtension() {
    }

    public FileExtension(long fileExtensionId, String fileExtension) {
        this.fileExtensionId = fileExtensionId;
        this.fileExtension = fileExtension;
    }

    public FileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public long getFileExtensionId() {
        return fileExtensionId;
    }

    public void setFileExtensionId(long fileExtensionId) {
        this.fileExtensionId = fileExtensionId;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
