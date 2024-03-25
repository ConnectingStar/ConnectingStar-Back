package connectingstar.tars.habit.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 내 종료한 습관 조회 요청
 *
 * @author 김성수
 */
@Getter
@Setter
public class QuitHabitListRequest {

    /**
     * 유저 ID
     */
    private Integer userId;
}
