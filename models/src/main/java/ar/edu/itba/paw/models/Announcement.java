package ar.edu.itba.paw.models;

import java.util.Date;

public class Announcement {

    private long id_announcement, id_teacher, id_subject;
    private Date date;
    private String title, content;

    public Announcement() {
    }

    public Announcement(long id_announcement, long id_teacher, long id_subject, Date date, String title, String content) {
        this.id_announcement = id_announcement;
        this.id_teacher = id_teacher;
        this.id_subject = id_subject;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public long getId_announcement() {
        return id_announcement;
    }

    public void setId_announcement(long id_announcement) {
        this.id_announcement = id_announcement;
    }

    public long getId_teacher() {
        return id_teacher;
    }

    public void setId_teacher(long id_teacher) {
        this.id_teacher = id_teacher;
    }

    public long getId_subject() {
        return id_subject;
    }

    public void setId_subject(long id_subject) {
        this.id_subject = id_subject;
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
