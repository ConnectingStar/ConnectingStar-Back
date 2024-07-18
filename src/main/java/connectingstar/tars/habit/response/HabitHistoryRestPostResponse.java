package connectingstar.tars.habit.response;

import connectingstar.tars.habit.dto.HabitHistoryDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 휴식 기록 생성 후 response DTO
 *
 * @author 이우진
 */
@Getter
@Setter
public class HabitHistoryRestPostResponse {
    /**
     * 생성된 습관 기록
     * isRest = true
     */
    private HabitHistoryDto habitHistory;
}
