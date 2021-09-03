package ar.edu.itba.paw.models;

import java.util.Date;

public class Announcement {

    private long announcementId, teacherId, courseId;
    private Date date;
    private String title, content;


    public Announcement(long teacherId, long courseId, Date date, String title, String content) {
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(long announcementId) {
        this.announcementId = announcementId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
