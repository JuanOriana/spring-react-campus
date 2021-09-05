package ar.edu.itba.paw.models;

import java.util.Date;

public class Announcement {

    private long announcementId, userId, courseId;
    private Date date;
    private String title, content;

    public Announcement(long announcementId, long userId, long courseId, Date date, String title, String content) {
        this.announcementId = announcementId;
        this.userId = userId;
        this.courseId = courseId;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public Announcement(long courseId, long userId, Date date, String title, String content) {
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
