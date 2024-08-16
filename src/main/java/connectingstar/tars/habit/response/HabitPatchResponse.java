package connectingstar.tars.habit.response;

import connectingstar.tars.habit.dto.RunHabitDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitPatchResponse {
    RunHabitDto runHabit;
}
