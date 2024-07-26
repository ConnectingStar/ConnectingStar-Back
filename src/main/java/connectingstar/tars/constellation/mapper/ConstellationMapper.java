package connectingstar.tars.constellation.mapper;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.domain.ConstellationType;
import connectingstar.tars.constellation.dto.ConstellationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ConstellationTypeMapper.class})
public interface ConstellationMapper {
    ConstellationMapper INSTANCE = Mappers.getMapper(ConstellationMapper.class);

    @Mapping(source = "constellation.type.constellationTypeId", target = "constellationTypeId")
    @Mapping(target = "type", ignore = true)
    @Mapping(source = "constellation.name", target = "name")
    ConstellationDto toDto(Constellation constellation);

    @Mapping(source = "constellation.type.constellationTypeId", target = "constellationTypeId")
    @Mapping(source = "constellationType", target = "type")
    @Mapping(source = "constellation.name", target = "name")
    ConstellationDto toDto(Constellation constellation, ConstellationType constellationType);
}
