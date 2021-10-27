package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcements_announcementid_seq")
    @SequenceGenerator(name = "announcements_announcementid_seq", sequenceName = "announcements_announcementid_seq", allocationSize = 1)
    private Long announcementId;

    @Column
    private LocalDateTime date;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User author;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    /* Default */ Announcement() {
        // Just for Hibernate
    }

    public static class Builder {

        private Long announcementId;
        private LocalDateTime date;
        private String title;
        private String content;
        private User author;
        private Course course;

        public Builder() {
        }

        Builder(Long announcementId, LocalDateTime date, String title, String content, User author, Course course) {
            this.announcementId = announcementId;
            this.date = date;
            this.title = title;
            this.content = content;
            this.author = author;
            this.course = course;
        }

        public Builder withAnnouncementId(Long announcementId){
            this.announcementId = announcementId;
            return Builder.this;
        }

        public Builder withDate(LocalDateTime date){
            this.date = date;
            return Builder.this;
        }

        public Builder withTitle(String title){
            this.title = title;
            return Builder.this;
        }

        public Builder withContent(String content){
            this.content = content;
            return Builder.this;
        }

        public Builder withAuthor(User author){
            this.author = author;
            return Builder.this;
        }

        public Builder withCourse(Course course){
            this.course = course;
            return Builder.this;
        }

        public Announcement build() {
            if(this.date == null){
                throw new NullPointerException("The property \"date\" is null. "
                        + "Please set the value by \"date()\". "
                        + "The properties \"date\", \"title\", \"content\" and \"author\" are required.");
            }
            if(this.title == null){
                throw new NullPointerException("The property \"title\" is null. "
                        + "Please set the value by \"title()\". "
                        + "The properties \"date\", \"title\", \"content\" and \"author\" are required.");
            }
            if(this.content == null){
                throw new NullPointerException("The property \"content\" is null. "
                        + "Please set the value by \"content()\". "
                        + "The properties \"date\", \"title\", \"content\" and \"author\" are required.");
            }
            if(this.author == null){
                throw new NullPointerException("The property \"author\" is null. "
                        + "Please set the value by \"author()\". "
                        + "The properties \"date\", \"title\", \"content\" and \"author\" are required.");
            }

            return new Announcement(this);
        }
    }

    private Announcement(Builder builder) {
        this.announcementId = builder.announcementId;
        this.date = builder.date;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.course = builder.course;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
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

    public void merge(Announcement announcement) {
        this.title = this.title.equals(announcement.getTitle()) ? this.title : announcement.getTitle();
        this.content = this.content.equals(announcement.getContent()) ? this.content : announcement.getContent();
        this.date = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return Objects.equals(announcementId, that.announcementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(announcementId);
    }
}
