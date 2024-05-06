package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * 진행중인 습관 생성 후 반환데이터
 *
 * @author 김성수
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunPostResponse {
    /**
     * 진행중인 습관 ID
     */
    private final Integer runHabitId;

    public RunPostResponse(Integer runHabitId) {
        this.runHabitId = runHabitId;
    }
}
