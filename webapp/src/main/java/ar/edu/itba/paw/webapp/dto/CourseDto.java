package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Course;

public class CourseDto {

    private long courseId;
    private int year;
    private int quarter;
    private String board;
    private SubjectDto subject;
    private String courseUri; //TODO: ver si el tipo "String" esta bien o va "URI"

    public static CourseDto fromCourse(Course course){
        if (course == null){
            return null;
        }

        final CourseDto dto = new CourseDto();
        dto.courseId = course.getCourseId();
        dto.year = course.getYear();
        dto.quarter = course.getQuarter();
        dto.board = course.getBoard();
        dto.subject = SubjectDto.fromSubject(course.getSubject());
        StringBuilder aux = new StringBuilder("/courses/"); //TODO: ver si el baseUrl se pasa como parametro o queda asi
        dto.courseUri = aux.append(course.getCourseId().toString()).toString();
        return dto;
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

    public String getCourseUri() {
        return courseUri;
    }

    public void setCourseUri(String courseUri) {
        this.courseUri = courseUri;
    }
}
