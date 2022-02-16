package ar.edu.itba.paw.webapp.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MailFormDto {
    @NotNull
    @NotBlank
    @Length(max=8)
    String title;

    @NotNull
    @NotBlank
    @Length(max=8)
    String content;

    @Min(1)
    @NotNull
    private Integer year;
    Long courseId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
