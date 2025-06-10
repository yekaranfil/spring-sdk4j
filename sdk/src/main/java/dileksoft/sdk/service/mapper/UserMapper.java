package dileksoft.sdk.service.mapper;


import dileksoft.sdk.dto.UserDTO;
import dileksoft.sdk.persistence.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Spring için bu satıra gerek yok ama istersen elle çağırmak için kalabilir
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserDTO dto);

    UserDTO toDto(User entity);
}