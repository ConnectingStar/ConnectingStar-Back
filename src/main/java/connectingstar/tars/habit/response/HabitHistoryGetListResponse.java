package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitHistoryGetListResponse {

    /**
     * 습관 수행 날짜
     */
    private final LocalDateTime runDate;

    /**
     * 실천한 장소
     */
    private final String runPlace;

    /**
     * 실천량
     */
    private final Integer runValue;

    /**
     * 단위
     */
    private final String unit;

    /**
     * 느낀점
     */
    private final String review;

    public HabitHistoryGetListResponse(LocalDateTime runDate, String runPlace, Integer runValue, String unit, String review) {
        this.runDate = runDate;
        this.runPlace = runPlace;
        this.runValue = runValue;
        this.unit = unit;
        this.review = review;
    }
}
