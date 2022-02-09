package ar.edu.itba.paw.webapp.common.mappers;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.webapp.dto.RoleDto;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleDto roleDtoFromRole(Role role);
}
