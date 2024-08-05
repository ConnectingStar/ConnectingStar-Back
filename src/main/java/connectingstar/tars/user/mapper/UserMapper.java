package connectingstar.tars.user.mapper;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.mapper.ConstellationMapper;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.dto.UserDto;
import connectingstar.tars.user.response.UserMeConstellationPatchResponse;
import connectingstar.tars.user.response.UserMeGetResponse;
import connectingstar.tars.user.response.UserMeOnboardingPatchResponse;
import connectingstar.tars.user.response.UserMeProfileGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ConstellationMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "profileConstellation.constellationId", target = "profileConstellationId")
    @Mapping(target = "profileConstellation", ignore = true)
    UserDto toDto(User user);

    @Mapping(source = "user.profileConstellation.constellationId", target = "profileConstellationId")
    @Mapping(target = "identity", source = "user.identity")
    @Mapping(target = "profileConstellation", source = "profileConstellation")
    UserDto toDto(User user, Constellation profileConstellation);

    @Mapping(source = "user", target = "user")
    UserMeGetResponse toMeGetResponse(User user);

    @Mapping(target = "user", expression = "java(toDto(user, profileConstellation))")
    @Mapping(target = "defaultCharacterImage", source = "defaultCharacterImage")
    UserMeProfileGetResponse toMeProfileGetResponse(User user, Constellation profileConstellation, String defaultCharacterImage);

    @Mapping(target = "user", source = "user")
    UserMeOnboardingPatchResponse toMeOnboardingPatchResponse(User user);

    @Mapping(target = "user", expression = "java(toDto(user, profileConstellation))")
    UserMeConstellationPatchResponse toMeConstellationPatchResponse(User user, Constellation profileConstellation);
}
