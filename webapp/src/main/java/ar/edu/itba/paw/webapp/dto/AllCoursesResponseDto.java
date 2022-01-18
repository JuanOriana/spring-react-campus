package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.stream.Collectors;

public class AllCoursesResponseDto {

    private List<Integer> availableYears;
    private List<CourseDto> courses;

    public static AllCoursesResponseDto responseFrom(List<Integer> years, List<Course> courses){
        if (years == null || courses == null){
            return null;
        }

        AllCoursesResponseDto dto = new AllCoursesResponseDto();
        dto.availableYears = years;
        dto.courses = courses.stream().map(CourseDto::fromCourse).collect(Collectors.toList());
        return dto;
    }

    public List<Integer> getAvailableYears() {
        return availableYears;
    }

    public void setAvailableYears(List<Integer> availableYears) {
        this.availableYears = availableYears;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }
}
