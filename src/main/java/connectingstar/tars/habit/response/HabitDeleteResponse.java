package connectingstar.tars.habit.response;

import connectingstar.tars.habit.dto.QuitHabitDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitDeleteResponse {
    /**
     * 삭제된 습관.
     * RunHabit은 삭제되고, QuitHabit이 생성된다.
     */
    QuitHabitDto quitHabit;
}
