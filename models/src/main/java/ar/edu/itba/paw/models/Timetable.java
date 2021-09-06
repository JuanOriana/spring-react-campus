package ar.edu.itba.paw.models;

import java.sql.Time;

public class Timetable {
    private int courseId, dayOfWeek;
    private Time startTime, endTime;

    public Timetable(int courseId, int dayOfWeek, Time startTime, Time endTime) {
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Timetable(int dayOfWeek, Time startTime, Time endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
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
        return startTime;
    }

    public void setBegins(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEnd() {
        return endTime;
    }

    public void setEnd(Time endTime) {
        this.endTime = endTime;
    }
}
