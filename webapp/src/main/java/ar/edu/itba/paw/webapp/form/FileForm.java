package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.constraint.annotation.NotEmptyFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.*;

public class FileForm {

    @NotEmptyFile
    private CommonsMultipartFile file;

    @Min(0)
    @Max(2147483647) // Value of the max integer in postgresql
    @NotNull
    private Long categoryId;

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @AssertTrue(message = "File must be provided")
    public boolean isFileProvided() {
        return (file != null) && ( ! file.isEmpty());
    }
}