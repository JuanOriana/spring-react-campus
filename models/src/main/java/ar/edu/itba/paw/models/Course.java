package ar.edu.itba.paw.models;

import java.time.Year;

public class Course {

    private long subjectId;
    private Year year;
    private float code;
    private Quarter quarter;
    private String board, name;
    // Board = Comision

    public Course() {
    }

    public Course(long subjectId, Year year, float code, Quarter quarter, String board, String name) {
        this.subjectId = subjectId;
        this.year = year;
        this.code = code;
        this.quarter = quarter;
        this.board = board;
        this.name = name;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public float getCode() {
        return code;
    }

    public void setCode(float code) {
        this.code = code;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
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

    private enum Quarter {
        FIRST_Q(1),
        SECOND_Q(2);

        private final int quarter_number;

        Quarter(final int number) {
            this.quarter_number = number;
        }

        public int getQuarter_number() {
            return quarter_number;
        }
    }
}
