package ar.edu.itba.paw.webapp.assembler;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.UserController;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler extends JaxRsResourceAssemblerSupport<User, UserDto> {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public UserAssembler() {
        super(UserController.class, UserDto.class);
    }

    @Override
    public UserDto toResource(User entity) {
        UserDto user = createResourceWithId(entity.getUserId(), entity);
        UserDto result = mapper.userToUserDto(entity);
        result.add(user.getLinks());
        result.add(JaxRsLinkBuilder.linkTo(UserController.class).slash(entity.getUserId()).slash("courses").withRel("courses"));
        result.add(JaxRsLinkBuilder.linkTo(UserController.class).slash(entity.getUserId()).slash("image").withRel("profile-image"));
        result.add(JaxRsLinkBuilder.linkTo(UserController.class).slash(entity.getUserId()).slash("timetable").withRel("timetable"));
        return result;
    }
}
