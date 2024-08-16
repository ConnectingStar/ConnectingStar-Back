package connectingstar.tars.habit.mapper;

import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.dto.QuitHabitDto;
import connectingstar.tars.habit.response.HabitDeleteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuitHabitMapper {
    QuitHabitMapper INSTANCE = Mappers.getMapper(QuitHabitMapper.class);

    @Mapping(target = "userId", source = "user.id")
    QuitHabitDto toDto(QuitHabit quitHabit);

    @Mapping(target = "quitHabit", source = "quitHabit")
    HabitDeleteResponse toHabitDeleteResponse(QuitHabit quitHabit);
}
