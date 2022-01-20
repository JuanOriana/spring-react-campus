package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.models.Answer;

import java.time.LocalDateTime;

public class AnswerDto {

    private Long answerId;

    private ExamDto exam;

    private LocalDateTime deliveredDate;

    private UserDto student;

    private UserDto teacher;

    private FileModelDto answerFile;

    private Float score;

    private String corrections;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public ExamDto getExam() {
        return exam;
    }

    public void setExam(ExamDto exam) {
        this.exam = exam;
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

    public static AnswerDto fromAnswer(Answer answer){
        if(answer == null){
            return null;
        }

        AnswerDto answerDto = new AnswerDto();

        answerDto.setAnswerFile(FileModelDto.fromFile(answer.getAnswerFile()));
        answerDto.setAnswerId(answer.getAnswerId());
        answerDto.setCorrections(answer.getCorrections());
        answerDto.setScore(answerDto.getScore());
        answerDto.setExam(ExamDto.fromExam(answer.getExam()));
        answerDto.setStudent(UserDto.fromUser(answer.getStudent()));
        answerDto.setTeacher(UserDto.fromUser(answer.getTeacher()));
        answerDto.setDeliveredDate(answer.getDeliveredDate());

        return answerDto;

    }
}
