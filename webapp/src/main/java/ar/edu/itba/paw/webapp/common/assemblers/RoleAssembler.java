package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.webapp.common.mappers.RoleMapper;
import ar.edu.itba.paw.webapp.controllers.SubjectController;
import ar.edu.itba.paw.webapp.controllers.UserController;
import ar.edu.itba.paw.webapp.dto.RoleDto;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleAssembler {

    private static final RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

    public RoleAssembler() {
        // For spring
    }

    public RoleDto toResource(Role entity) {
        RoleDto result = mapper.roleDtoFromRole(entity);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(UserController.class).slash("roles").slash(entity.getRoleId()).withSelfRel());
        result.setLinks(links);
        return result;
    }

    public List<RoleDto> toResources(List<Role> roles) {
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach(r -> roleDtos.add(toResource(r)));
        return roleDtos;
    }
}
