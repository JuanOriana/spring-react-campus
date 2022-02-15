package ar.edu.itba.paw.webapp.constraints.validators;

import ar.edu.itba.paw.webapp.security.api.exceptions.DtoValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class DtoConstraintValidator {

    @Autowired
    private Validator validator;

    public <T> void validate(T dto, String message, Class<?>... groups) throws DtoValidationException {

        final Set<ConstraintViolation<T>> constraintViolations = validator.validate(dto, groups);

        if (!constraintViolations.isEmpty())
            throw new DtoValidationException(message, constraintViolations);
    }
}