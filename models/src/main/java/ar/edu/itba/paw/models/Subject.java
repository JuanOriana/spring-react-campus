package ar.edu.itba.paw.models;

import java.time.Year;

public class Subject {

    private long id_subject;
    private Year year;
    private float code;
    private int quarter;
    private String board, name;
    // Board = Comision

    public Subject() {
    }

    public Subject(long id_subject, Year year, float code, int quarter, String board, String name) {
        this.id_subject = id_subject;
        this.year = year;
        this.code = code;
        this.quarter = quarter;
        this.board = board;
        this.name = name;
    }

    public long getId_subject() {
        return id_subject;
    }

    public void setId_subject(long id_subject) {
        this.id_subject = id_subject;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
