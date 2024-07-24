package connectingstar.tars.habit.response;

import connectingstar.tars.history.dto.HabitHistoryDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 습관 기록 생성 후 response DTO
 *
 * @author 이우진
 */
@Getter
@Setter
public class HabitHistoryPostResponse {
    /**
     * 생성된 습관 기록
     */
    private HabitHistoryDto habitHistory;
}
