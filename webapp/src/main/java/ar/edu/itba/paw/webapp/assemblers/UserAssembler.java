package ar.edu.itba.paw.webapp.assemblers;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controllers.UserController;
import ar.edu.itba.paw.webapp.dtos.user.UserDto;
import ar.edu.itba.paw.webapp.mappers.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAssembler {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public UserAssembler() {
        // For spring
    }

    public UserDto toResource(User entity, boolean showDeepLinks) {
        UserDto result = mapper.userToUserDto(entity);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(UserController.class).slash(entity.getUserId()).withSelfRel());
        if(showDeepLinks) {
            Link coursesLink = new Link(
                    new UriTemplate(
                            JaxRsLinkBuilder.linkTo(UserController.class).slash(entity.getUserId()).slash("courses").toString(),
                            new TemplateVariables(new TemplateVariable("page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                    ), "courses"
            );
            links.add(coursesLink);
            links.add(JaxRsLinkBuilder.linkTo(UserController.class).slash(entity.getUserId()).slash("image").withRel("profile-image"));
            links.add(JaxRsLinkBuilder.linkTo(UserController.class).slash(entity.getUserId()).slash("timetable").withRel("timetable"));
            result.setLinks(links);
        }
        return result;
    }

    public List<UserDto> toResources(List<User> users, boolean showDeepLinks) {
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(u -> userDtoList.add(toResource(u, showDeepLinks)));
        return userDtoList;
    }
}
