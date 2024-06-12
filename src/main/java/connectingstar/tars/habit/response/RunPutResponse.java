package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import connectingstar.tars.common.domain.TimeInfo;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.RunHabit;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

import java.time.LocalTime;
import org.jetbrains.annotations.NotNull;

/**
 * 진행중인 습관 수정 응답
 *
 * @author 김성수
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"runHabitId", "userId", "userName", "identity", "runTime", "place", "action", "value", "unit", "firstAlert", "secondAlert","historyInfo"})
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
    private final TimeInfo runTime;

    /**
     * 장소
     */
    private final String place;

    /**
     * 행동
     */
    private final String behavior;

    /**
     * 얼마나
     */
    private final Integer behaviorValue;

    /**
     * 단위
     */
    private final String behaviorUnit;

    /**
     * 1차 알림
     */
    private final TimeInfo firstAlert;

    /**
     * 2차 알림
     */
    private final TimeInfo secondAlert;

    public RunPutResponse(RunHabit runHabit, LocalTime firstAlert, LocalTime secondAlert) {
        this.runHabitId = runHabit.getRunHabitId();
        this.userId = runHabit.getUser().getId();
        this.userNickname = runHabit.getUser().getNickname();
        this.identity = runHabit.getIdentity();
        this.runTime = new TimeInfo(runHabit.getRunTime());
        this.place = runHabit.getPlace();
        this.behavior = runHabit.getAction();
        this.behaviorValue = runHabit.getValue();
        this.behaviorUnit = runHabit.getUnit();
        this.firstAlert = new TimeInfo(firstAlert);
        this.secondAlert = new TimeInfo(secondAlert);
    }

    public RunPutResponse(RunHabit runHabit) {
        this.runHabitId = runHabit.getRunHabitId();
        this.userId = runHabit.getUser().getId();
        this.userNickname = runHabit.getUser().getNickname();
        this.identity = runHabit.getIdentity();
        this.runTime = new TimeInfo(runHabit.getRunTime());
        this.place = runHabit.getPlace();
        this.behavior = runHabit.getAction();
        this.behaviorValue = runHabit.getValue();
        this.behaviorUnit = runHabit.getUnit();
        this.firstAlert = new TimeInfo(runHabit.getAlerts().get(0).getAlertTime());
        this.secondAlert = new TimeInfo(runHabit.getAlerts().get(1).getAlertTime());
    }
}
