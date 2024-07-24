package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.common.domain.TimeInfo;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 온보딩 응답 반환
 *
 * @author 김성수
 * @deprecated use {@link UserMeOnboardingPatchResponse} instead
 */
@Deprecated
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOnboardingResponse {
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
     * 1차 알림
     */
    private TimeInfo firstAlert;

    /**
     * 2차 알림
     */
    private TimeInfo secondAlert;

    public UserOnboardingResponse(User user, RunHabit runHabit) {
        this.nickname = user.getNickname();
        this.genderType = user.getGender().getCode();
        this.ageRangeType = user.getAgeRange().getCode();
        this.referrer = user.getReferrer();
        this.identity = user.getIdentity();
        this.runTime = new TimeInfo(runHabit.getRunTime());
        this.place = runHabit.getPlace();
        this.behavior = runHabit.getAction();
        this.behaviorValue = runHabit.getValue();
        this.behaviorUnit = runHabit.getUnit();
        this.firstAlert = new TimeInfo(runHabit.getAlerts().get(0).getAlertTime());
        this.secondAlert = new TimeInfo(runHabit.getAlerts().get(1).getAlertTime());
    }
}
