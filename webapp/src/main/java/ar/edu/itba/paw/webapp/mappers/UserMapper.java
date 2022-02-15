package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dtos.user.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto userToUserDto(User user);
}
