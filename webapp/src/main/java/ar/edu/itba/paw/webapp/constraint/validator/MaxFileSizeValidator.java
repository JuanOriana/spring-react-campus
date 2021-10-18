package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.MaxFileSize;
import ar.edu.itba.paw.webapp.constraint.annotation.NotEmptyFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, CommonsMultipartFile> {

    private long maxSize;
    public void initialize(MaxFileSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.value();
    }

    public boolean isValid(CommonsMultipartFile file, ConstraintValidatorContext constraintContext) {
        if (file == null){
            return true;
        }
        return file.getSize() <= maxSize*1024*1024;
    }

}