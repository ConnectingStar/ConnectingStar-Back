package connectingstar.tars.habit.request;


import lombok.Getter;
import lombok.Setter;

/**
 * 내 습관 기록 기간 조회 요청
 *
 * @author 김성수
 */
@Getter
@Setter
public class HabitHistoryGetListRequest {

    /**
     * 유저 ID
     */
    private Integer userId;

    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;

    /**
     * 최신,오래된 순 구분
     */
    private Boolean increase;

    /**
     * 휴식 여부 구분
     */
    private Boolean isRest;
}
