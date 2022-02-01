package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.FileExtension;

public class FileExtensionDto {

    private long fileExtensionId;
    private String fileExtension;

    public static FileExtensionDto fromFileExtension(FileExtension fileExtension){
        if (fileExtension == null){
            return null;
        }

        final FileExtensionDto dto = new FileExtensionDto();
        dto.fileExtensionId = fileExtension.getFileExtensionId();
        dto.fileExtension = fileExtension.getFileExtensionName();
        return dto;

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
