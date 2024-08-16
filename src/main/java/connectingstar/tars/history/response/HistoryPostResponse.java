package connectingstar.tars.history.response;

import connectingstar.tars.history.dto.HabitHistoryDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryPostResponse {
    /**
     * 생성된 습관 기록
     */
    private HabitHistoryDto habitHistory;
}
