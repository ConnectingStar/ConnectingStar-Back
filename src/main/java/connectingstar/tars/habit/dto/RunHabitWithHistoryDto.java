package connectingstar.tars.habit.dto;

import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.RunHabit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RunHabitWithHistoryDto {
    private RunHabit runHabit;
    private HabitHistory habitHistory;
}
