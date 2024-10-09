package connectingstar.tars.habit.request;


import connectingstar.tars.common.domain.TimeInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 진행중인 습관 생성 요청
 *
 * @author 김성수
 * @deprecated use {@link connectingstar.tars.habit.request.HabitPostRequest} instead
 */
@Deprecated
@Getter
@Setter
public class RunPostRequest {


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
