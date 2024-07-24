package connectingstar.tars.habit.response;

import connectingstar.tars.habit.dto.HabitHistoryDto;
import connectingstar.tars.habit.dto.RunHabitDto;
import connectingstar.tars.habit.enums.DailyTrackingStatus;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * /v2/habits/daily-trackings의 response DTO.
 */
@Getter
@Setter
@Builder
public class HabitDailyTrackingGetResponse {
    @Nullable
    private HabitHistoryDto history;

    private RunHabitDto habit;

    /**
     * 습관 카드에 표시할 상태.
     * 미완료, 완료, 휴식, 만료됨.
     */
    private DailyTrackingStatus status;
}
