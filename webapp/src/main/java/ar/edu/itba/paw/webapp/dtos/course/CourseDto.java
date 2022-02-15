package ar.edu.itba.paw.webapp.dtos.course;

import ar.edu.itba.paw.webapp.dtos.subject.SubjectDto;
import org.springframework.hateoas.Link;

import java.io.Serializable;
import java.util.List;

public class CourseDto implements Serializable {

    private long courseId;
    private int year;
    private int quarter;
    private String board;
    private SubjectDto subject;
    private List<Link> links;

    public CourseDto() {
        // For MessageBodyWriter
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

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
}
