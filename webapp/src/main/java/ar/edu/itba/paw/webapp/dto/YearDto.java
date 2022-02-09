package ar.edu.itba.paw.webapp.dto;

import java.io.Serializable;


public class YearDto implements Serializable {

    private Integer year;

    public YearDto() {
        // For Jax-rs
    }

    public YearDto(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
