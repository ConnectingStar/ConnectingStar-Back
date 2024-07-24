package connectingstar.tars.habit.dto;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.history.domain.HabitHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RunHabitAndHistoryDto {
    private RunHabit runHabit;
    private HabitHistory habitHistory;
}
