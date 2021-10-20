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


    /* Default */ Timetable() {
        // Just for Hibernate
    }

    public Timetable(Course course, int dayOfWeek, Time startTime, Time endTime) {
        this.primaryKey = new Pk();
        this.course = course;
        this.primaryKey.courseId = course.getCourseId();
        this.primaryKey.dayOfWeek = dayOfWeek;
        this.primaryKey.startTime = startTime;
        this.primaryKey.endTime = endTime;
    }

    public Timetable(int dayOfWeek, Time startTime, Time endTime) {
        this.primaryKey.dayOfWeek = dayOfWeek;
        this.primaryKey.startTime = startTime;
        this.primaryKey.endTime = endTime;
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
        return primaryKey.startTime;
    }

    public void setBegins(Time startTime) {
        this.primaryKey.startTime = startTime;
    }

    public Time getEnd() {
        return primaryKey.endTime;
    }

    public void setEnd(Time endTime) {
        this.primaryKey.endTime = endTime;
    }


    private static class Pk implements Serializable {

        @Column(nullable = false, updatable = false)
        private Long courseId;

        @Column
        private Integer dayOfWeek;

        @Column
        private Time startTime;

        @Column
        private Time endTime;



        public Integer getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(Integer dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public Time getStartTime() {
            return startTime;
        }

        public void setStartTime(Time startTime) {
            this.startTime = startTime;
        }

        public Time getEndTime() {
            return endTime;
        }

        public void setEndTime(Time endTime) {
            this.endTime = endTime;
        }
    }


}
