package connectingstar.tars.user.mapper;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.mapper.ConstellationMapper;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.dto.UserDto;
import connectingstar.tars.user.dto.UserWithConstellationDto;
import connectingstar.tars.user.response.UserMeGetResponse;
import connectingstar.tars.user.response.UserMeOnboardingPatchResponse;
import connectingstar.tars.user.response.UserMeProfileGetResponse;
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
    @Mapping(target = "constellation", source = "constellation")
    UserWithConstellationDto toWithConstellationDto(User user, Constellation constellation);

    @Mapping(source = "user", target = "user")
    UserMeGetResponse toMeGetResponse(User user);

    @Mapping(target = "user", expression = "java(toWithConstellationDto(user, constellation))")
    @Mapping(target = "defaultCharacterImage", source = "defaultCharacterImage")
    UserMeProfileGetResponse toMeProfileGetResponse(User user, Constellation constellation, String defaultCharacterImage);

    @Mapping(target = "user", source = "user")
    UserMeOnboardingPatchResponse toMeOnboardingPatchResponse(User user);
}
