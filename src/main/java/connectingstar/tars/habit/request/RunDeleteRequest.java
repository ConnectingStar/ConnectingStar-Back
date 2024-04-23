package connectingstar.tars.habit.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 진행중인 습관 삭제 요청
 *
 * @author 김성수
 */
@Getter
@Setter
public class RunDeleteRequest {

    /**
     * 사용자 PK
     */
    private Integer userId;

    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;

    /**
     * 삭제 이유
     */
    private String reason;
}
