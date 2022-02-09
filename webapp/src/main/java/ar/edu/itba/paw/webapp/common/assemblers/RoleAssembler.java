package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.webapp.common.mappers.RoleMapper;
import ar.edu.itba.paw.webapp.controllers.UserController;
import ar.edu.itba.paw.webapp.dto.RoleDto;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class RoleAssembler extends JaxRsResourceAssemblerSupport<Role, RoleDto> {

    private static final RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

    public RoleAssembler() {
        super(UserController.class, RoleDto.class);
    }

    @Override
    public RoleDto toResource(Role entity) {
        RoleDto roleDto = createResourceWithId(entity.getRoleId(), entity);
        RoleDto result = mapper.roleDtoFromRole(entity);
        result.add(JaxRsLinkBuilder.linkTo(UserController.class).slash("roles").slash(entity.getRoleId()).withSelfRel());
        return result;
    }
}
