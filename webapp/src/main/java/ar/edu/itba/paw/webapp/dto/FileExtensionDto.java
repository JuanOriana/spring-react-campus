package ar.edu.itba.paw.webapp.dto;

import org.springframework.hateoas.ResourceSupport;

public class FileExtensionDto extends ResourceSupport {

    private long fileExtensionId;
    private String fileExtensionName;

    public long getFileExtensionId() {
        return fileExtensionId;
    }

    public void setFileExtensionId(long fileExtensionId) {
        this.fileExtensionId = fileExtensionId;
    }

    public String getFileExtensionName() {
        return fileExtensionName;
    }

    public void setFileExtensionName(String fileExtensionName) {
        this.fileExtensionName = fileExtensionName;
    }
}
