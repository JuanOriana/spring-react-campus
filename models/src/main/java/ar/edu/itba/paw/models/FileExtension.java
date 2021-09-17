package ar.edu.itba.paw.models;

public class FileExtension {

    private Long fileExtensionId;
    private String fileExtension;

    public FileExtension() {
    }

    public FileExtension(Long fileExtensionId, String fileExtension) {
        this.fileExtensionId = fileExtensionId;
        this.fileExtension = fileExtension;
    }

    public FileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Long getFileExtensionId() {
        return fileExtensionId;
    }

    public void setFileExtensionId(Long fileExtensionId) {
        this.fileExtensionId = fileExtensionId;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
