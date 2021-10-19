package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file_extensions")
public class FileExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fileextension_fileextensionid_seq")
    @SequenceGenerator(sequenceName = "fileextension_fileextensionid_seq", name = "fileextension_fileextensionid_seq", allocationSize = 1)
    @Column(name = "fileExtensionId")
    private Long fileExtensionId;

    @Column(length = 5, nullable = false, unique = true)
    private String fileExtensionName;

    /* Default */ FileExtension() {
        // Just for Hibernate
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileExtension that = (FileExtension) o;
        return Objects.equals(fileExtensionId, that.fileExtensionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileExtensionId);
    }
}
