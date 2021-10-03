package ar.edu.itba.paw.models.exception;

public class DuplicateCourseException extends RuntimeException {
    private final Integer year;
    private final Integer quarter;
    private final String board;
    private final Long subjectId;

    public static class Builder {

        private Integer year;
        private Integer quarter;
        private String board;
        private Long subjectId;

        public Builder() {
        }

        Builder(Integer year, Integer quarter, String board, Long subjectId) {
            this.year = year;
            this.quarter = quarter;
            this.board = board;
            this.subjectId = subjectId;
        }

        public Builder withYear(Integer year){
            this.year = year;
            return Builder.this;
        }

        public Builder withQuarter(Integer quarter){
            this.quarter = quarter;
            return Builder.this;
        }

        public Builder withBoard(String board){
            this.board = board;
            return Builder.this;
        }

        public Builder withSubjectId(Long subjectId){
            this.subjectId = subjectId;
            return Builder.this;
        }

        public DuplicateCourseException build() {
            return new DuplicateCourseException(this);
        }
    }

    private DuplicateCourseException(Builder builder) {
        this.year = builder.year;
        this.quarter = builder.quarter;
        this.board = builder.board;
        this.subjectId = builder.subjectId;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public String getBoard() {
        return board;
    }

    public Long getSubjectId() {
        return subjectId;
    }
}
