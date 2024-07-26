package connectingstar.tars.constellation.mapper;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.domain.ConstellationType;
import connectingstar.tars.constellation.dto.ConstellationDto;
import connectingstar.tars.constellation.dto.UserConstellationDto;
import connectingstar.tars.user.domain.UserConstellation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserConstellationMapper {
    UserConstellationMapper INSTANCE = Mappers.getMapper(UserConstellationMapper.class);

    @Mapping(source = "userConstellation.user.id", target = "userId")
    @Mapping(source = "userConstellation.constellation.constellationId", target = "constellationId")
    @Mapping(source = "userConstellation.starCount", target = "starCount")
    @Mapping(target = "constellation", expression = "java(toConstellationDto(constellation, constellationType))")
    UserConstellationDto toDto(UserConstellation userConstellation, Constellation constellation, ConstellationType constellationType);

    @Named("toConstellationDto")
    @Mapping(source = "constellation.type.constellationTypeId", target = "constellationTypeId")
    @Mapping(source = "constellationType", target = "type")
    @Mapping(source = "constellation.name", target = "name")
    ConstellationDto toConstellationDto(Constellation constellation, ConstellationType constellationType);
}
