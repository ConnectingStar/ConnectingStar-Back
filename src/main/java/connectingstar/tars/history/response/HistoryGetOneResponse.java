package connectingstar.tars.history.response;

import connectingstar.tars.history.dto.HabitHistoryWithRunHabitDto;
import lombok.Getter;
import lombok.Setter;

/**
 * /v2/histories/{id} 의 응답.
 */
@Getter
@Setter
public class HistoryGetOneResponse {
    private HabitHistoryWithRunHabitDto history;
}
