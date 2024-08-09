package connectingstar.tars.user.mapper;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.mapper.ConstellationMapper;
import connectingstar.tars.constellation.mapper.UserConstellationMapper;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.dto.UserDto;
import connectingstar.tars.user.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ConstellationMapper.class, UserConstellationMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "profileConstellation.constellationId", target = "profileConstellationId")
    @Mapping(source = "unlockingConstellation.userConstellationId", target = "unlockingUserConstellationId")
    @Mapping(target = "profileConstellation", ignore = true)
    UserDto toDto(User user);

    @Mapping(source = "user.profileConstellation.constellationId", target = "profileConstellationId")
    @Mapping(source = "user.unlockingConstellation.userConstellationId", target = "unlockingUserConstellationId")
    @Mapping(target = "identity", source = "user.identity")
    @Mapping(target = "profileConstellation", source = "profileConstellation")
    @Mapping(target = "unlockingConstellation", ignore = true)
    UserDto toDto(User user, Constellation profileConstellation);

    @Mapping(source = "user.profileConstellation.constellationId", target = "profileConstellationId")
    @Mapping(source = "user.unlockingConstellation.userConstellationId", target = "unlockingUserConstellationId")
    @Mapping(target = "identity", source = "user.identity")
    @Mapping(target = "profileConstellation", ignore = true)
    @Mapping(target = "unlockingConstellation", source = "unlockingConstellation", qualifiedByName = {"UserConstellationMapper", "toDto(UserConstellation)"})
    UserDto toDto(User user, UserConstellation unlockingConstellation);

    @Mapping(source = "user", target = "user")
    UserMeGetResponse toMeGetResponse(User user);

    @Mapping(target = "user", expression = "java(toDto(user, profileConstellation))")
    @Mapping(target = "defaultCharacterImage", source = "defaultCharacterImage")
    UserMeProfileGetResponse toMeProfileGetResponse(User user, Constellation profileConstellation, String defaultCharacterImage);

    @Mapping(target = "user", source = "user")
    UserMeOnboardingPatchResponse toMeOnboardingPatchResponse(User user);

    @Mapping(target = "user", expression = "java(toDto(user, unlockingConstellation))")
    UserMeConstellationPostResponse toMeConstellationPostResponse(User user, UserConstellation unlockingConstellation);

    @Mapping(target = "user", expression = "java(toDto(user, profileConstellation))")
    UserMeProfileConstellationPatchResponse toMeConstellationPatchResponse(User user, Constellation profileConstellation);

}
