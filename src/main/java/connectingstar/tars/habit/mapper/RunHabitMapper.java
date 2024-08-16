package connectingstar.tars.habit.mapper;

import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.RunHabitDto;
import connectingstar.tars.habit.response.HabitGetOneResponse;
import connectingstar.tars.habit.response.HabitPatchResponse;
import connectingstar.tars.habit.response.HabitPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HabitAlertMapper.class})
public interface RunHabitMapper {
    RunHabitMapper INSTANCE = Mappers.getMapper(RunHabitMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "habitAlerts", ignore = true)
    RunHabitDto toDto(RunHabit runHabit);

    @Named("toDto(RunHabit, List<HabitAlert>)")
    @Mapping(source = "runHabit.user.id", target = "userId")
    @Mapping(target = "habitAlerts", source = "habitAlerts")
    RunHabitDto toDto(RunHabit runHabit, List<HabitAlert> habitAlerts);

    List<RunHabitDto> toDtoList(List<RunHabit> runHabits);

    @Mapping(target = "runHabit", expression = "java( toDto(runHabit, habitAlerts) )")
    HabitGetOneResponse toGetOneResponse(RunHabit runHabit, List<HabitAlert> habitAlerts);

    @Mapping(source = "runHabit", target = "runHabit")
    HabitPostResponse toPostResponse(RunHabit runHabit);

    @Mapping(target = "runHabit", expression = "java( toDto(runHabit, habitAlerts) )")
    HabitPatchResponse toPatchResponse(RunHabit runHabit, List<HabitAlert> habitAlerts);
}
