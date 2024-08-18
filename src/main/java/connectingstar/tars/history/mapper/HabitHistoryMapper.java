package connectingstar.tars.history.mapper;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.mapper.RunHabitMapper;
import connectingstar.tars.habit.response.HabitHistoryPostResponse;
import connectingstar.tars.habit.response.HabitHistoryRestPostResponse;
import connectingstar.tars.habit.response.HistoryRestPostResponse;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.history.dto.HabitHistoryDto;
import connectingstar.tars.history.dto.HabitHistoryWithRunHabitDto;
import connectingstar.tars.history.response.HistoryGetOneResponse;
import connectingstar.tars.history.response.HistoryPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RunHabitMapper.class})
public interface HabitHistoryMapper {
    HabitHistoryMapper INSTANCE = Mappers.getMapper(HabitHistoryMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "runHabit.runHabitId", target = "runHabitId")
    @Mapping(target = "runHabit", ignore = true)
    HabitHistoryDto toDto(HabitHistory habitHistory);

    @Mapping(source = "habitHistory.user.id", target = "userId")
    @Mapping(source = "habitHistory.runHabit.runHabitId", target = "runHabitId")
    @Mapping(source = "habitHistory.action", target = "action")
    @Mapping(source = "runHabit", target = "runHabit")
    HabitHistoryWithRunHabitDto toWithRunHabitDto(HabitHistory habitHistory, RunHabit runHabit);

    @Mapping(target = "history", expression = "java(toWithRunHabitDto(history, runHabit))")
    HistoryGetOneResponse toGetOneResponse(HabitHistory history, RunHabit runHabit);

    @Deprecated
    @Mapping(source = "habitHistory", target = "habitHistory")
    HabitHistoryPostResponse toPostResponseV1(HabitHistory habitHistory);

    @Mapping(source = "habitHistory", target = "habitHistory")
    HistoryPostResponse toPostResponse(HabitHistory habitHistory);

    @Deprecated
    @Mapping(source = "habitHistory", target = "habitHistory")
    HabitHistoryRestPostResponse toRestPostResponseV1(HabitHistory habitHistory);

    @Mapping(source = "habitHistory", target = "habitHistory")
    HistoryRestPostResponse toRestPostResponse(HabitHistory habitHistory);
}
