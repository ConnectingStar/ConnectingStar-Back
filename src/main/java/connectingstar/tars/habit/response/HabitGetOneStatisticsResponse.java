package connectingstar.tars.habit.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HabitGetOneStatisticsResponse {
    /**
     * 누적 획득 별
     */
    private Integer totalStarCount = 0;

    /**
     * 누적 실천량
     */
    private Integer totalValue = 0;
}
