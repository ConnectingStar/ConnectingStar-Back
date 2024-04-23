package connectingstar.tars.habit.request;


import lombok.Getter;
import lombok.Setter;


/**
 * 오늘 실천한 습관을 저장 요청
 *
 * @author 김성수
 */
@Getter
@Setter
public class HistoryPostRequest {

    /**
     * 유저 ID
     */
    private Integer userId;

    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;

    /**
     * 만족도
     */
    private Integer achievement;

    /**
     * 실천한 장소
     */
    private String runPlace;


    /**
     * 실천량
     */
    private Integer runValue;

    /**
     * 느낀점
     */
    private String review;

    /**
     * 휴식여부
     */
    private Boolean isRest;
}