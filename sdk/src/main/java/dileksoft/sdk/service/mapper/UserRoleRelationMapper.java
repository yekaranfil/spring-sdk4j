package dileksoft.sdk.service.mapper;

import dileksoft.sdk.dto.UserRoleRelationDTO;
import dileksoft.sdk.persistence.domain.UserRoleRelation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserRoleRelationMapper {

    // Spring için bu satıra gerek yok ama istersen elle çağırmak için kalabilir
    UserRoleRelationMapper INSTANCE = Mappers.getMapper(UserRoleRelationMapper.class);

    UserRoleRelation toEntity(UserRoleRelationDTO dto);

    UserRoleRelationDTO toDto(UserRoleRelation entity);
}
