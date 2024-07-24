package connectingstar.tars.user.mapper;

import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "constellationType.constellationTypeId", target = "constellationTypeId")
    UserDto toDto(User user);
}
