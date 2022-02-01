package ar.edu.itba.paw.webapp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class ExceptionDto {

    private String message;
    private List<FieldViolationDto> errors;

    public ExceptionDto() {}

    public ExceptionDto(final String message) {
        this.setMessage(message);
    }

    public ExceptionDto(final String message, final Set<? extends ConstraintViolation<?>> constraintViolations) {
        this.setMessage(message);
        errors = new ArrayList<>(constraintViolations.size());

        constraintViolations.forEach((constraintViolation) -> {
            if (!constraintViolation.getPropertyPath().toString().isEmpty())
                errors.add(new FieldViolationDto(constraintViolation));
        });
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public List<FieldViolationDto> getErrors() {
        return errors;
    }

    public void setErrors(final List<FieldViolationDto> errors) {
        this.errors = errors;
    }

}