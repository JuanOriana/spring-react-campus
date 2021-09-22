package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.NotEmptyFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyFileValidator implements ConstraintValidator<NotEmptyFile, CommonsMultipartFile> {

    public void initialize(NotEmptyFile constraintAnnotation) {
    }

    public boolean isValid(CommonsMultipartFile file, ConstraintValidatorContext constraintContext) {
        return file != null && !file.isEmpty();
    }

}