package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 습관 기록 조회 응답
 *
 * @author 김성수
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"runDate","achievement","runValue"})
public class HistoryListResponse {

    /**
     * 습관 수행 날짜
     */
    private final LocalDateTime runDate;

    /**
     * 만족도
     */
    private final Integer achievement;

    /**
     * 실천량
     */
    private final Integer runValue;

    public HistoryListResponse(LocalDateTime runDate, Integer achievement, Integer runValue) {
        this.runDate = runDate;
        this.achievement = achievement;
        this.runValue = runValue;
    }
}