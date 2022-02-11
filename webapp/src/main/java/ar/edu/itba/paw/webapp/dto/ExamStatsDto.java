package ar.edu.itba.paw.webapp.dto;

import org.springframework.hateoas.Link;
import java.io.Serializable;
import java.util.List;

public class ExamStatsDto implements Serializable {

    private ExamDto exam;
    private List<AnswerDto> corrected;
    private List<AnswerDto> notCorrected;
    private Double average;
    private List<Link> links;


    public ExamStatsDto() {
        // For Jax-Rs
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public ExamDto getExam() {
        return exam;
    }

    public void setExam(ExamDto exam) {
        this.exam = exam;
    }

    public List<AnswerDto> getCorrected() {
        return corrected;
    }

    public void setCorrected(List<AnswerDto> corrected) {
        this.corrected = corrected;
    }

    public List<AnswerDto> getNotCorrected() {
        return notCorrected;
    }

    public void setNotCorrected(List<AnswerDto> notCorrected) {
        this.notCorrected = notCorrected;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
