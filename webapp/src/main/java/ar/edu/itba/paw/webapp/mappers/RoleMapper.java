package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.webapp.dtos.RoleDto;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleDto roleDtoFromRole(Role role);
}
