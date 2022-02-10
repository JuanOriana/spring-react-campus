package ar.edu.itba.paw.webapp.dto;

import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

public class ExamDto extends ResourceSupport {

    private Long examId;

    private CourseDto course;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String title;

    private String description;

    private FileModelDto examFile;

    private Double average;




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

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
