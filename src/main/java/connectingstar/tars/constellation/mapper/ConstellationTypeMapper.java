package connectingstar.tars.constellation.mapper;

import connectingstar.tars.constellation.domain.ConstellationType;
import connectingstar.tars.constellation.dto.ConstellationTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConstellationTypeMapper {
    ConstellationTypeMapper INSTANCE = Mappers.getMapper(ConstellationTypeMapper.class);

    ConstellationTypeDto toDto(ConstellationType constellationType);
}
