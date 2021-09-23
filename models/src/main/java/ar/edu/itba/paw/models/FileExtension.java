package ar.edu.itba.paw.models;

public class FileExtension {

    private Long fileExtensionId;
    private String fileExtensionName;

    public FileExtension() {
    }

    public FileExtension(Long fileExtensionId, String fileExtension) {
        this.fileExtensionId = fileExtensionId;
        this.fileExtensionName = fileExtension;
    }

    public FileExtension(String fileExtension) {
        this.fileExtensionName = fileExtension;
    }

    public Long getFileExtensionId() {
        return fileExtensionId;
    }

    public void setFileExtensionId(Long fileExtensionId) {
        this.fileExtensionId = fileExtensionId;
    }

    public String getFileExtensionName() {
        return fileExtensionName;
    }

    public void setFileExtensionName(String fileExtensionName) {
        this.fileExtensionName = fileExtensionName;
    }
}
