package connectingstar.tars.habit.mapper;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.RunHabitDto;
import connectingstar.tars.habit.response.HabitGetOneResponse;
import connectingstar.tars.habit.response.HabitPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RunHabitMapper {
    RunHabitMapper INSTANCE = Mappers.getMapper(RunHabitMapper.class);

    @Mapping(source = "user.id", target = "userId")
    RunHabitDto toDto(RunHabit runHabit);

    List<RunHabitDto> toDtoList(List<RunHabit> runHabits);

    @Mapping(source = "runHabit", target = "runHabit")
    HabitGetOneResponse toGetOneResponse(RunHabit runHabit);

    @Mapping(source = "runHabit", target = "runHabit")
    HabitPostResponse toPostResponse(RunHabit runHabit);
}
