package connectingstar.tars.habit.request;

import connectingstar.tars.common.domain.TimeInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 진행중인 습관 수정 요청
 *
 * @author 김성수
 */
@Getter
@Setter
public class RunPutRequest {


    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;

    /**
     * 사용자 PK
     */
    private Long userId;

    /**
     * 정체성
     */
    private String identity;

    /**
     * 실천 시간
     */
    private TimeInfo runTime;

    /**
     * 장소
     */
    private String place;

    /**
     * 행동
     */
    private String behavior;

    /**
     * 얼마나
     */
    private Integer behaviorValue;

    /**
     * 단위
     */
    private String behaviorUnit;

    /**
     * 1차 알림 (값이 없을 시 자동으로 runTime 10분 전으로 설정)
     */
    private TimeInfo firstAlert;

    /**
     * 2차 알림 (값이 없을 시 자동으로 runTime 30분 후로 설정)
     */
    private TimeInfo secondAlert;
}
