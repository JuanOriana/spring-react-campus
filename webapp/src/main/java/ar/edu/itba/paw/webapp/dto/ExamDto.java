package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Exam;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;

public class ExamDto {

    private Long examId;

    private CourseDto course;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String title;

    private String description;

    private FileModelDto examFile;

    private Double average;

    private String url;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }


    public static ExamDto fromExam(UriInfo uriInfo, Exam exam,Double average){
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
        examDto.setAverage(average);

        examDto.setUrl(uriInfo.getBaseUriBuilder().path("exams").path(String.valueOf(exam.getCourse().getCourseId())).path(String.valueOf(exam.getExamId())).build().toString());

        return examDto;
    }
}
