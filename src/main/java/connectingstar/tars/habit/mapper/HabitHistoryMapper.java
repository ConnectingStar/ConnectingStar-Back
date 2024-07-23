package connectingstar.tars.habit.mapper;

import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.HabitHistoryDto;
import connectingstar.tars.habit.response.HabitHistoryDateGetResponseV1;
import connectingstar.tars.habit.response.HabitHistoryPostResponse;
import connectingstar.tars.habit.response.HabitHistoryRestPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RunHabitMapper.class})
public interface HabitHistoryMapper {
    HabitHistoryMapper INSTANCE = Mappers.getMapper(HabitHistoryMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "runHabit.runHabitId", target = "runHabitId")
    HabitHistoryDto toDto(HabitHistory habitHistory);

    @Mapping(source = "habitHistory", target = "habitHistory")
    @Mapping(source = "runHabit", target = "runHabit")
    HabitHistoryDateGetResponseV1 toDateGetResponse(HabitHistory habitHistory, RunHabit runHabit);

    @Mapping(source = "habitHistory", target = "habitHistory")
    HabitHistoryPostResponse toPostResponse(HabitHistory habitHistory);

    @Mapping(source = "habitHistory", target = "habitHistory")
    HabitHistoryRestPostResponse toRestPostResponse(HabitHistory habitHistory);
}
