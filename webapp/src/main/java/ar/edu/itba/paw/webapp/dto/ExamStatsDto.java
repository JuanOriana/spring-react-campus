package ar.edu.itba.paw.webapp.dto;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class ExamStatsDto extends ResourceSupport {

    ExamDto examDto;
    List<AnswerDto> corrected;
    List<AnswerDto> notCorrected;
    Double average;

    public ExamStatsDto() {
        // For Jax-Rs
    }

    public ExamStatsDto(ExamDto examDto) {
        this.examDto = examDto;
    }

    public ExamDto getExamDto() {
        return examDto;
    }

    public void setExamDto(ExamDto examDto) {
        this.examDto = examDto;
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
