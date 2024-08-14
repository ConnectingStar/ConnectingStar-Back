package connectingstar.tars.habit.response;

import connectingstar.tars.habit.dto.RunHabitDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HabitGetListResponse {
    List<RunHabitDto> runHabits;
}
