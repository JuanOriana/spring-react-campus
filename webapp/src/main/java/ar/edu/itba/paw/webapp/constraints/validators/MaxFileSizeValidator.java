package ar.edu.itba.paw.webapp.constraints.validators;

import ar.edu.itba.paw.webapp.constraints.annotations.MaxFileSize;

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