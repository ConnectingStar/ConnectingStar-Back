package connectingstar.tars.habit.response;

import connectingstar.tars.habit.dto.QuitHabitDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuitHabitDeleteResponse {
    /**
     * 삭제된 종료 습관
     */
    QuitHabitDto quitHabit;
}
