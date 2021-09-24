package ar.edu.itba.paw.webapp.constraint.annotation;

import ar.edu.itba.paw.webapp.constraint.validator.NotEmptyFileValidator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NotEmptyFileValidator.class)
@Documented
public @interface NotEmptyFile {

    String message() default "A file must be attached.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
