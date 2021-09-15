package ar.edu.itba.paw.models;

import java.sql.Time;

public class Timetable {
    private Integer courseId, dayOfWeek;
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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
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
