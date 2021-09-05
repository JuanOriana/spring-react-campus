package ar.edu.itba.paw.models;

import java.sql.Time;

public class Timetable {
    private int courseId, dayOfWeek;
    private Time begins, end;

    public Timetable(int courseId, int dayOfWeek, Time begins, Time end) {
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.begins = begins;
        this.end = end;
    }

    public Timetable(int dayOfWeek, Time begins, Time end) {
        this.dayOfWeek = dayOfWeek;
        this.begins = begins;
        this.end = end;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Time getBegins() {
        return begins;
    }

    public void setBegins(Time begins) {
        this.begins = begins;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }
}
