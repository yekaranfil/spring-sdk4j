package dileksoft.sdk.service.mapper;


import dileksoft.sdk.dto.RoleDTO;
import dileksoft.sdk.persistence.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // Spring için bu satıra gerek yok ama istersen elle çağırmak için kalabilir
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toEntity(RoleDTO dto);

    RoleDTO toDto(Role entity);
}
