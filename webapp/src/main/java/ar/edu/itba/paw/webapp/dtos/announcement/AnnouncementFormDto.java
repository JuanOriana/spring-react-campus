package ar.edu.itba.paw.webapp.dtos.announcement;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AnnouncementFormDto {

    @NotNull
    @Size(min=2,max=50)
    private String title;

    @NotNull
    @Size(min=2)
    private String content;

    @NotNull
    private String redirectLink;

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
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
