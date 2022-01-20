package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Exam;

import java.time.LocalDateTime;

public class ExamDto {

    private Long examId;

    private CourseDto course;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String title;

    private String description;

    private FileModelDto examFile;


    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FileModelDto getExamFile() {
        return examFile;
    }

    public void setExamFile(FileModelDto examFile) {
        this.examFile = examFile;
    }

    public static ExamDto fromExam(Exam exam){
        if (exam == null){
            return null;
        }

        final ExamDto examDto = new ExamDto();
        examDto.setExamId(exam.getExamId());
        examDto.setExamFile(FileModelDto.fromFile(exam.getExamFile()));
        examDto.setCourse(CourseDto.fromCourse(exam.getCourse()));
        examDto.setDescription(exam.getDescription());
        examDto.setEndTime(exam.getEndTime());
        examDto.setStartTime(exam.getStartTime());
        examDto.setTitle(exam.getTitle());

        return examDto;
    }
}
