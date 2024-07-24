package connectingstar.tars.history.mapper;

import connectingstar.tars.habit.mapper.RunHabitMapper;
import connectingstar.tars.habit.response.HabitHistoryPostResponse;
import connectingstar.tars.habit.response.HabitHistoryRestPostResponse;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.history.dto.HabitHistoryDto;
import connectingstar.tars.history.response.HistoryGetOneResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RunHabitMapper.class})
public interface HabitHistoryMapper {
    HabitHistoryMapper INSTANCE = Mappers.getMapper(HabitHistoryMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "runHabit.runHabitId", target = "runHabitId")
    HabitHistoryDto toDto(HabitHistory habitHistory);

    @Mapping(source = "history", target = "history")
    HistoryGetOneResponse toGetOneResponse(HabitHistory history);

    @Mapping(source = "habitHistory", target = "habitHistory")
    HabitHistoryPostResponse toPostResponse(HabitHistory habitHistory);

    @Mapping(source = "habitHistory", target = "habitHistory")
    HabitHistoryRestPostResponse toRestPostResponse(HabitHistory habitHistory);
}
