package connectingstar.tars.habit.mapper;

import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.response.HabitHistoryPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HabitHistoryMapper {
    HabitHistoryMapper INSTANCE = Mappers.getMapper(HabitHistoryMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "runHabit.runHabitId", target = "runHabitId")
    HabitHistoryPostResponse toPostResponse(HabitHistory habitHistory);
}
