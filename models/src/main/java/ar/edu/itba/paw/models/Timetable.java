package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "timetables")
public class Timetable {

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @Column
    private Integer dayOfWeek;

    @Column
    private Time startTime;

    @Column
    private Time endTime;

    /* Default */ Timetable() {
        // Just for Hibernate
    }

    public Timetable(Course course, int dayOfWeek, Time startTime, Time endTime) {
        this.course = course;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Timetable(int dayOfWeek, Time startTime, Time endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
