package ar.edu.itba.paw.models;

import java.util.Date;

public class Announcement {

    private long announcementId;
    private Date date;
    private String title, content;
    private User author;
    private Course course;


    public Announcement(long announcementId, Date date, String title, String content, User author, Course course) {
        this.announcementId = announcementId;
        this.date = date;
        this.title = title;
        this.content = content;
        this.author = author;
        this.course = course;
    }

    public Announcement(Date date, String title, String content, User author, Course course) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.author = author;
        this.course = course;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(long announcementId) {
        this.announcementId = announcementId;
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
