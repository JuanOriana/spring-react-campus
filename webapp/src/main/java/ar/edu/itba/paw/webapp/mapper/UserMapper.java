package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
