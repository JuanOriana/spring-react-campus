package ar.edu.itba.paw.webapp.dto;

import javax.validation.constraints.Size;

public class AnnouncementFormDto {
    @Size(min=2,max=50)
    private String title;

    @Size(min=2)
    private String content;

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
