package ar.edu.itba.paw.webapp.dto;

import org.springframework.hateoas.Link;

import java.io.Serializable;
import java.util.List;

public class FileExtensionDto implements Serializable {

    private long fileExtensionId;
    private String fileExtensionName;
    private List<Link> links;

    public FileExtensionDto() {

    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

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
