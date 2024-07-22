package connectingstar.tars.habit.response;

import connectingstar.tars.habit.dto.HabitHistoryDto;
import lombok.Getter;
import lombok.Setter;

/**
 * GET /habit/history/date API의 response
 */
@Getter
@Setter
public class HabitHistoryDateGetResponse {
    private final HabitHistoryDto habitHistory;


}
