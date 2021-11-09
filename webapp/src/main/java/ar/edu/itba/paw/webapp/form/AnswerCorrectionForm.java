package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class AnswerCorrectionForm{

    @Size(min=2,max=50)
    private String comments;

    @Max(10)
    @Min(0)
    @NotBlank
    private Float mark;


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }
}