package ar.edu.itba.paw.webapp.common.mappers;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDto userToUserDto(User user);
}
