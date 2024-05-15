package connectingstar.tars.user.request;

import connectingstar.tars.common.domain.TimeInfo;
import lombok.Getter;
import lombok.Setter;


/**
 * 회원 온보딩
 *
 * @author 김성수, 김규리
 */

@Getter
@Setter
public class UserOnboardingRequest {

    /**
     * 사용할 닉네임
     */
    private String nickname;

    /**
     * 사용할 성별
     */
    private String genderType;

    /**
     * 사용할 나이대
     */
    private String ageRangeType;

    /**
     * 들어온 경로
     */
    private String referrer;

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
