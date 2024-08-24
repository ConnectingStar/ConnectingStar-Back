package connectingstar.tars.habit.mapper;

import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.dto.QuitHabitDto;
import connectingstar.tars.habit.response.HabitDeleteResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Named("QuitHabitMapper")
@Mapper(componentModel = "spring")
public interface QuitHabitMapper {
    QuitHabitMapper INSTANCE = Mappers.getMapper(QuitHabitMapper.class);

    @Named("toDto(QuitHabit)")
    @Mapping(target = "userId", source = "user.id")
    QuitHabitDto toDto(QuitHabit quitHabit);

    @IterableMapping(qualifiedByName = {"QuitHabitMapper", "toDto(QuitHabit)"})
    List<QuitHabitDto> toDtoList(List<QuitHabit> quitHabits);

    @Mapping(target = "quitHabit", source = "quitHabit")
    HabitDeleteResponse toHabitDeleteResponse(QuitHabit quitHabit);
}
