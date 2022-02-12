package ar.edu.itba.paw.webapp.dto.answer;

import java.io.Serializable;

public class AnswerCorrectionDto implements Serializable {
    private Float score;
    private String correction;

    public AnswerCorrectionDto() {
        // For MessageBodyWriter
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }
}
