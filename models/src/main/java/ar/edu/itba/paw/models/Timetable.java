package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "timetables")
public class Timetable {

    @EmbeddedId
    private Pk primaryKey;

    @ManyToOne
    @JoinColumn(name = "courseId", insertable = false, updatable = false)
    private Course course;

    @Column
    private Time startTime;

    @Column
    private Time endTime;


    /* Default */ Timetable() {
        // Just for Hibernate
    }

    public Timetable(Course course, int dayOfWeek, Time startTime, Time endTime) {
        this.primaryKey = new Pk();
        this.course = course;
        this.primaryKey.courseId = course.getCourseId();
        this.primaryKey.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Timetable(int dayOfWeek, Time startTime, Time endTime) {
        this.primaryKey.dayOfWeek = dayOfWeek;
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
        return primaryKey.dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.primaryKey.dayOfWeek = dayOfWeek;
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


    private static class Pk implements Serializable {

        @Column(nullable = false, updatable = false)
        private Long courseId;

        @Column
        private Integer dayOfWeek;

        public Integer getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(Integer dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

    }


}
