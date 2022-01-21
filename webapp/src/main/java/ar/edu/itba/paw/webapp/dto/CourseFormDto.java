package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class CourseFormDto {

    @Min(0)
    @Max(2147483647) // Value of the max integer in postgresql
    @NotNull
    private Long subjectId;

    @Min(1)
    @Max(2)
    @NotNull
    private Integer quarter;

    @NotNull
    @NotBlank
    @Length(max=8)
    private String board;

    @Min(0)
    @NotNull
    private Integer year;

    private List<Integer> startTimes = Arrays.asList(new Integer[7]);

    private List<Integer> endTimes = Arrays.asList(new Integer[7]);

    public static CourseFormDto fromCourse(Course course, List<Integer> startTimes, List<Integer> endTimes){
        if (course == null || startTimes == null || endTimes == null){
            return null;
        }

        CourseFormDto dto = new CourseFormDto();
        dto.board = course.getBoard();
        dto.quarter = course.getQuarter();
        dto.subjectId = course.getSubject().getSubjectId();
        dto.year = course.getYear();
        dto.startTimes = startTimes;
        dto.endTimes = endTimes;
        return dto;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Integer> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(List<Integer> startTimes) {
        this.startTimes = startTimes;
    }

    public List<Integer> getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(List<Integer> endTimes) {
        this.endTimes = endTimes;
    }

}
