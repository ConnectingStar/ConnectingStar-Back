package connectingstar.tars.history.dto;

import connectingstar.tars.habit.dto.RunHabitDto;
import lombok.Getter;
import lombok.Setter;

/**
 * runHabit이 포함된 HabitHistory DTO
 */
@Getter
@Setter
public class HabitHistoryWithRunHabitDto extends HabitHistoryDto {
    private RunHabitDto runHabit;
}
