package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.constraint.annotation.NotEmptyFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UserProfileForm {

    @NotEmptyFile
    private CommonsMultipartFile image;

    public CommonsMultipartFile getImage() {
        return image;
    }

    public void setImage(CommonsMultipartFile image) {
        this.image = image;
    }
}
