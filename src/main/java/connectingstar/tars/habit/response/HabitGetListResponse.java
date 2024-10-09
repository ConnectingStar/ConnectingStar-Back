package connectingstar.tars.habit.response;

import connectingstar.tars.habit.dto.RunHabitDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class HabitGetListResponse {
    List<RunHabitDto> runHabits;
}
