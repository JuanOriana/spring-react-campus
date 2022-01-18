package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Announcement;

import java.time.LocalDateTime;

public class AnnouncementDto {

    private String title;

    private String content;

    private UserDto author;

    private LocalDateTime time;

    private CourseDto course;


    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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

    public static AnnouncementDto fromAnnouncement(Announcement announcement){
        AnnouncementDto dto = new AnnouncementDto();

        dto.setAuthor(UserDto.fromUser(announcement.getAuthor()));
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setTime(announcement.getDate());
        dto.setCourse(CourseDto.fromCourse(announcement.getCourse()));

        return dto;
    }
}
