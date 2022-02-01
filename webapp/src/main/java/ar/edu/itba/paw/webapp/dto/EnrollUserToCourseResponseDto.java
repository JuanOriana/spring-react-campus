package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollUserToCourseResponseDto {

    private List<UserDto> unenrolledUsers;
    private List<RoleDto> roles;

    public static EnrollUserToCourseResponseDto fromUserToCourseInformation(List<User> unenrolledUsers, List<Role> roles){
        if (unenrolledUsers == null || roles == null){
            return null;
        }

        EnrollUserToCourseResponseDto dto = new EnrollUserToCourseResponseDto();
        dto.unenrolledUsers = unenrolledUsers.stream().map(UserDto::fromUser).collect(Collectors.toList());
        dto.roles = roles.stream().map(RoleDto::fromRole).collect(Collectors.toList());
        return dto;
    }

    public List<UserDto> getUnenrolledUsers() {
        return unenrolledUsers;
    }

    public void setUnenrolledUsers(List<UserDto> unenrolledUsers) {
        this.unenrolledUsers = unenrolledUsers;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
