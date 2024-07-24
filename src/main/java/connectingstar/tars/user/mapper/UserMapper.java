package connectingstar.tars.user.mapper;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.mapper.ConstellationMapper;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.dto.UserDto;
import connectingstar.tars.user.dto.UserWithConstellationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ConstellationMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "constellation.constellationId", target = "constellationId")
    UserDto toDto(User user);

    @Mapping(source = "constellation.constellationId", target = "constellationId")
    @Mapping(source = "user.identity", target = "identity")
    @Mapping(source = "constellation", target = "constellation")
    UserWithConstellationDto toWithConstellationDto(User user, Constellation constellation);
}
