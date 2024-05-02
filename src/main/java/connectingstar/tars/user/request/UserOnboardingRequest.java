package connectingstar.tars.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime runTime;

    /**
     * 장소
     */
    private String place;

    /**
     * 행동
     */
    private String action;

    /**
     * 얼마나
     */
    private Integer value;

    /**
     * 단위
     */
    private String unit;

    /**
     * 1차 알림 (값이 없을 시 자동으로 runTime 10분 전으로 설정)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime firstAlert;

    /**
     * 2차 알림 (값이 없을 시 자동으로 runTime 30분 후로 설정)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime secondAlert;

}
