package ar.edu.itba.paw.models;
import java.time.LocalDateTime;

public class Announcement {

    private Integer announcementId;
    private LocalDateTime date;
    private String title, content;
    private User author;
    private Course course;


    public Announcement(Integer announcementId, LocalDateTime date, String title, String content, User author, Course course) {
        this.announcementId = announcementId;
        this.date = date;
        this.title = title;
        this.content = content;
        this.author = author;
        this.course = course;
    }

    public Announcement(LocalDateTime date, String title, String content, User author, Course course) {
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

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
