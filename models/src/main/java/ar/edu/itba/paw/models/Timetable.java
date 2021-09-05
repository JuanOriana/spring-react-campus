package ar.edu.itba.paw.models;

public class Timetable {
    private int courseId, dayOfWeek;
    private long begins, duration;

    public Timetable(int courseId, int dayOfWeek, long begins, long duration) {
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.begins = begins;
        this.duration = duration;
    }

    public Timetable(int dayOfWeek, long begins, long duration) {
        this.dayOfWeek = dayOfWeek;
        this.begins = begins;
        this.duration = duration;
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

    public long getBegins() {
        return begins;
    }

    public void setBegins(long begins) {
        this.begins = begins;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
