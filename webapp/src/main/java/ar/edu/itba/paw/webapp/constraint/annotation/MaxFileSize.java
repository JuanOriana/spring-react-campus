package ar.edu.itba.paw.webapp.constraint.annotation;

import ar.edu.itba.paw.webapp.constraint.validator.MaxFileSizeValidator;
import ar.edu.itba.paw.webapp.constraint.validator.NotEmptyFileValidator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//Determines if file size is to large. The parameter is the maximum file size in mega bytes
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = MaxFileSizeValidator.class)
@Documented
public @interface MaxFileSize {

    String message() default "This file is too big.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long value();

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        long[] value();
    }

}
