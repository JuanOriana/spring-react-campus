package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.GenericEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollUserToCourseResponseDto {

    private CourseDto course;
    private List<UserDto> unenrolledUsers;
    private List<UserDto> enrolledStudents;
    private List<UserDto> courseTeachers;
    private List<UserDto> courseHelpers;
    private List<RoleDto> roles;

    public static EnrollUserToCourseResponseDto fromUserToCourseInformation(Course course, List<User> unenrolledUsers, List<User> enrolledStudents, List<User> courseTeachers, List<User> courseHelpers, List<Role> roles){
        if (course == null || unenrolledUsers == null || enrolledStudents == null || courseTeachers == null || courseHelpers == null || roles == null){
            return null;
        }

        EnrollUserToCourseResponseDto dto = new EnrollUserToCourseResponseDto();
        dto.course = CourseDto.fromCourse(course);
        dto.unenrolledUsers = unenrolledUsers.stream().map(UserDto::fromUser).collect(Collectors.toList());
        dto.courseTeachers = courseTeachers.stream().map(UserDto::fromUser).collect(Collectors.toList());
        dto.courseHelpers = courseHelpers.stream().map(UserDto::fromUser).collect(Collectors.toList());
        dto.roles = roles.stream().map(RoleDto::fromRole).collect(Collectors.toList());
        return dto;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public List<UserDto> getUnenrolledUsers() {
        return unenrolledUsers;
    }

    public void setUnenrolledUsers(List<UserDto> unenrolledUsers) {
        this.unenrolledUsers = unenrolledUsers;
    }

    public List<UserDto> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<UserDto> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public List<UserDto> getCourseTeachers() {
        return courseTeachers;
    }

    public void setCourseTeachers(List<UserDto> courseTeachers) {
        this.courseTeachers = courseTeachers;
    }

    public List<UserDto> getCourseHelpers() {
        return courseHelpers;
    }

    public void setCourseHelpers(List<UserDto> courseHelpers) {
        this.courseHelpers = courseHelpers;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
