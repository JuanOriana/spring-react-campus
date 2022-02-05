package ar.edu.itba.paw.webapp.dto;

import org.springframework.hateoas.ResourceSupport;

public class CourseDto extends ResourceSupport {

    private long courseId;
    private int year;
    private int quarter;
    private String board;
    private SubjectDto subject;
    private String uri;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String courseUri) {
        this.uri = courseUri;
    }
}
