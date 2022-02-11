package ar.edu.itba.paw.webapp.dto;


import org.springframework.hateoas.Link;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class AnswerDto implements Serializable {
    private Long answerId;
    private LocalDateTime deliveredDate;
    private UserDto student;
    private UserDto teacher;
    private FileModelDto answerFile;
    private Float score;
    private String corrections;
    private List<Link> links;

    public AnswerDto() {
        // For MessageBody
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public UserDto getStudent() {
        return student;
    }

    public void setStudent(UserDto student) {
        this.student = student;
    }

    public UserDto getTeacher() {
        return teacher;
    }

    public void setTeacher(UserDto teacher) {
        this.teacher = teacher;
    }

    public FileModelDto getAnswerFile() {
        return answerFile;
    }

    public void setAnswerFile(FileModelDto answerFile) {
        this.answerFile = answerFile;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getCorrections() {
        return corrections;
    }

    public void setCorrections(String corrections) {
        this.corrections = corrections;
    }
}
