package ar.edu.itba.paw.webapp.dto;

import javax.validation.ConstraintViolation;

public class FieldViolationDto {

    private String field;
    private String violation;

    public FieldViolationDto() {}

    public FieldViolationDto(final ConstraintViolation<?> constraintViolation) {
        this.setField(constraintViolation.getPropertyPath().toString());
        this.setViolation(constraintViolation.getMessage());
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

}