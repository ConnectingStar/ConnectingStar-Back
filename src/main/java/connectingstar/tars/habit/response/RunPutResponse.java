package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import connectingstar.tars.habit.domain.RunHabit;
import lombok.Getter;


import java.time.LocalTime;

/**
 * 진행중인 습관 수정 응답
 *
 * @author 김성수
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"runHabitId", "userId","userName","identity", "runTime", "place", "action", "value", "unit", "firstAlert","secondAlert"})
public class RunPutResponse {


    /**
     * 진행중인 습관 ID
     */
    private final Integer runHabitId;

    /**
     * 사용자 PK
     */
    private final Integer userId;


    /**
     * 사용자 이름
     */
    private final String userNickname;

    /**
     * 정체성
     */
    private final String identity;

    /**
     * 실천 시간
     */
    private final LocalTime runTime;

    /**
     * 장소
     */
    private final String place;

    /**
     * 행동
     */
    private final String action;

    /**
     * 얼마나
     */
    private final Integer value;

    /**
     * 단위
     */
    private final String unit;

    /**
     * 1차 알림
     */
    private final LocalTime firstAlert;

    /**
     * 2차 알림
     */
    private final LocalTime secondAlert;

    public RunPutResponse(RunHabit runHabit,LocalTime firstAlert,LocalTime secondAlert) {
        this.runHabitId = runHabit.getRunHabitId();
        this.userId = runHabit.getUser().getId();
        this.userNickname = runHabit.getUser().getNickname();
        this.identity = runHabit.getIdentity();
        this.runTime = runHabit.getRunTime();
        this.place = runHabit.getPlace();
        this.action = runHabit.getAction();
        this.value = runHabit.getValue();
        this.unit = runHabit.getUnit();
        this.firstAlert = firstAlert;
        this.secondAlert = secondAlert;
    }
}
