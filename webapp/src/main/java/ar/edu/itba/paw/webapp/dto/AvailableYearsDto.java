package ar.edu.itba.paw.webapp.dto;

public class AvailableYearsDto {
    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public static AvailableYearsDto fromYear(Integer year){
        AvailableYearsDto dto = new AvailableYearsDto();

        dto.setYear(year);

        return dto;
    }
}
