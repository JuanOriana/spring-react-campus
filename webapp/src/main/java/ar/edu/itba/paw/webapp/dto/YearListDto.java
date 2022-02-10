package ar.edu.itba.paw.webapp.dto;

import java.io.Serializable;
import java.util.List;


public class YearListDto implements Serializable {

    private List<Integer> years;

    public YearListDto(List<Integer> years) {
        this.years = years;
    }

    public YearListDto() {
        // For MessageBody
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }
}
