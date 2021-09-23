package ar.edu.itba.paw.models;

import java.sql.Time;

public class Timetable {
    private Long courseId;
    private Integer dayOfWeek;
    private Time startTime;
    private Time endTime;

    public Timetable(Long courseId, int dayOfWeek, Time startTime, Time endTime) {
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
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
