package ar.edu.itba.paw.webapp.constraints.validators;

import ar.edu.itba.paw.webapp.constraints.annotations.NotEmptyFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyFileValidator implements ConstraintValidator<NotEmptyFile, CommonsMultipartFile> {

    public void initialize(NotEmptyFile constraintAnnotation) {
        // Do nothing
    }

    public boolean isValid(CommonsMultipartFile file, ConstraintValidatorContext constraintContext) {
        return file != null && !file.isEmpty();
    }

}
