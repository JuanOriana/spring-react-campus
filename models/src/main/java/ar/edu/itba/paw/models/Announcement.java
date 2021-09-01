package ar.edu.itba.paw.models;

import java.util.Date;

public class Announcement {

    private long announcementId, teacherId, subjectId;
    private Date date;
    private String title, content;

    public Announcement() {
    }

    public Announcement(long announcementId, long teacherId, long subjectId, Date date, String title, String content) {
        this.announcementId = announcementId;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
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

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
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
