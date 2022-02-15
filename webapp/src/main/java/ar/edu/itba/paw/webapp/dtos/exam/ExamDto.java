package ar.edu.itba.paw.webapp.dtos.exam;

import ar.edu.itba.paw.webapp.dtos.file.FileModelDto;
import ar.edu.itba.paw.webapp.dtos.course.CourseDto;
import org.springframework.hateoas.Link;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ExamDto implements Serializable {
    private Long examId;
    private CourseDto course;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String description;
    private FileModelDto examFile;
    private Double average;
    private List<Link> links;

    public ExamDto() {
        // For MessageBodyWriter
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

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
