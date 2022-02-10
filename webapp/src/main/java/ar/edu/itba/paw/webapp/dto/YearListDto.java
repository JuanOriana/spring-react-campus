package ar.edu.itba.paw.webapp.dto;

import java.util.List;


public class YearListDto {

    private List<Integer> years;

    public YearListDto(List<Integer> years) {
        this.years = years;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }
}
