
package ar.edu.itba.paw.models;

public class Course {

    private long courseId;
    private Integer year;
    private Integer quarter;
    private String board;
    Subject subject;

    public Course(long courseId, Integer year, Integer quarter, String board, Subject subject) {
        this.courseId = courseId;
        this.year = year;
        this.quarter = quarter;
        this.board = board;
        this.subject = subject;
    }

    public Course(Integer year, Integer quarter, String board, Subject subject) {
        this.year = year;
        this.quarter = quarter;
        this.board = board;
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

}

