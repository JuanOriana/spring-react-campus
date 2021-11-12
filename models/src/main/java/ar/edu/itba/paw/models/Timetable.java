package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "timetables")
public class Timetable {

    @EmbeddedId
    private Pk primaryKey;

    @ManyToOne
    @JoinColumn(name = "courseId", insertable = false, updatable = false)
    private Course course;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;


    /* Default */ Timetable() {
        // Just for Hibernate
    }

    public Timetable(Course course, int dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.primaryKey = new Pk();
        this.course = course;
        this.primaryKey.courseId = course.getCourseId();
        this.primaryKey.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Timetable(int dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.primaryKey = new Pk();
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

    public LocalTime getBegins() {
        return startTime;
    }

    public void setBegins(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEnd() {
        return endTime;
    }

    public void setEnd(LocalTime endTime) {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pk pk = (Pk) o;
            return Objects.equals(courseId, pk.courseId) && Objects.equals(dayOfWeek, pk.dayOfWeek);
        }

        @Override
        public int hashCode() {
            return Objects.hash(courseId, dayOfWeek);
        }
    }


}
