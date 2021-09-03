
package ar.edu.itba.paw.models;

import java.time.Year;

public class Course {

    private long courseId; //
    private Integer year;
    private String code;
    private Integer quarter;
    private String board, name;
    // Board = Comision

    
    public Course( Integer year, String code, Integer quarter, String board, String name) {
        this.year = year;
        this.code = code;
        this.quarter = quarter;
        this.board = board;
        this.name = name;
    }

    public long getcourseId() {
        return courseId;
    }

    public void setcourseId(long courseId) {
        this.courseId = courseId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

