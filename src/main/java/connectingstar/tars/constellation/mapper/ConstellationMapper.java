package connectingstar.tars.constellation.mapper;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.dto.ConstellationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConstellationMapper {
    ConstellationMapper INSTANCE = Mappers.getMapper(ConstellationMapper.class);

    @Mapping(source = "type.constellationTypeId", target = "constellationTypeId")
    ConstellationDto toDto(Constellation constellation);
}
