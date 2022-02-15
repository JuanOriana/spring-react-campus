package ar.edu.itba.paw.webapp.dtos.announcement;

import ar.edu.itba.paw.webapp.dtos.course.CourseDto;
import ar.edu.itba.paw.webapp.dtos.user.UserDto;
import org.springframework.hateoas.Link;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class AnnouncementDto implements Serializable {

    private Long announcementId;

    private String title;

    private String content;

    private UserDto author;

    private LocalDateTime date;

    private CourseDto course;

    private List<Link> links;

    public AnnouncementDto() {
        // For MessageBodyWriter
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
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
