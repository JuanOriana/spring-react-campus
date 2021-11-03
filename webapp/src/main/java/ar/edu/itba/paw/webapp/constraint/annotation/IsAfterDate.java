package ar.edu.itba.paw.webapp.constraint.annotation;

import ar.edu.itba.paw.webapp.constraint.validator.IsAfterDateValidator;
import ar.edu.itba.paw.webapp.constraint.validator.MaxFileSizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//Determines if a date comes after another, as presented in its string representation
@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = IsAfterDateValidator.class)
@Documented
public @interface IsAfterDate {

    String message() default "End date should come before begin date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    String startDate();
    String endDate();

    @Target({ TYPE, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        IsAfterDate[] value();
    }

}
