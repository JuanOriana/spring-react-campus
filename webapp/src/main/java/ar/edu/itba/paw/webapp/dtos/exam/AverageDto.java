package ar.edu.itba.paw.webapp.dtos.exam;

import java.io.Serializable;

public class AverageDto implements Serializable {
    private Double average;

    public AverageDto(Double average) {
        this.average = average;
    }

    public AverageDto() {
        // For MessageBody
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
