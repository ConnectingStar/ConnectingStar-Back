package connectingstar.tars.habit.mapper;

import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.dto.HabitAlertDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HabitAlertMapper {
    HabitAlertMapper INSTANCE = Mappers.getMapper(HabitAlertMapper.class);

    @Mapping(source = "runHabit.runHabitId", target = "runHabitId")
    HabitAlertDto toDto(HabitAlert habitAlert);
}
