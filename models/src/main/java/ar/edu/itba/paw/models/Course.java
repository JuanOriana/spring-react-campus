package ar.edu.itba.paw.models;

import java.util.Objects;

public class Course {

    private Long courseId;
    private Integer year;
    private Integer quarter;
    private String board;
    private Subject subject;

    public static class Builder {

        private Long courseId;
        private Integer year;
        private Integer quarter;
        private String board;
        private Subject subject;

        public Builder() {
        }

        Builder(Long courseId, Integer year, Integer quarter, String board, Subject subject) {
            this.courseId = courseId;
            this.year = year;
            this.quarter = quarter;
            this.board = board;
            this.subject = subject;
        }

        public Builder withCourseId(Long courseId){
            this.courseId = courseId;
            return Builder.this;
        }

        public Builder withYear(Integer year){
            this.year = year;
            return Builder.this;
        }

        public Builder withQuarter(Integer quarter){
            this.quarter = quarter;
            return Builder.this;
        }

        public Builder withBoard(String board){
            this.board = board;
            return Builder.this;
        }

        public Builder withSubject(Subject subject){
            this.subject = subject;
            return Builder.this;
        }

        public Course build() {
            if(this.courseId == null){
                throw new NullPointerException("The property \"courseId\" is null. "
                        + "Please set the value by \"courseId()\". "
                        + "The properties \"courseId\", \"year\", \"quarter\", \"board\" and \"subject\" are required.");
            }
            if(this.year == null){
                throw new NullPointerException("The property \"year\" is null. "
                        + "Please set the value by \"year()\". "
                        + "The properties \"courseId\", \"year\", \"quarter\", \"board\" and \"subject\" are required.");
            }
            if(this.quarter == null){
                throw new NullPointerException("The property \"quarter\" is null. "
                        + "Please set the value by \"quarter()\". "
                        + "The properties \"courseId\", \"year\", \"quarter\", \"board\" and \"subject\" are required.");
            }
            if(this.board == null){
                throw new NullPointerException("The property \"board\" is null. "
                        + "Please set the value by \"board()\". "
                        + "The properties \"courseId\", \"year\", \"quarter\", \"board\" and \"subject\" are required.");
            }
            if(this.subject == null){
                throw new NullPointerException("The property \"subject\" is null. "
                        + "Please set the value by \"subject()\". "
                        + "The properties \"courseId\", \"year\", \"quarter\", \"board\" and \"subject\" are required.");
            }

            return new Course(this);
        }
    }

    private Course(Builder builder) {
        this.courseId = builder.courseId;
        this.year = builder.year;
        this.quarter = builder.quarter;
        this.board = builder.board;
        this.subject = builder.subject;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
}