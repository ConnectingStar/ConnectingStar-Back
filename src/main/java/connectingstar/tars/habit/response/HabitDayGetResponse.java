package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import connectingstar.tars.common.domain.TimeInfo;
import connectingstar.tars.habit.domain.RunHabit;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"runHabitId", "userNickname", "identity", "runTime", "place", "behavior", "behaviorValue", "behaviorUnit", "habitStatus"})
public class HabitDayGetResponse {

  /**
   * 진행중인 습관 ID
   */
  private final Integer runHabitId;

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
   * 단위
   */
  private final Integer habitStatus;


  public HabitDayGetResponse(Integer runHabitId, String userNickname, String identity, TimeInfo runTime, String place, String behavior,
      Integer behaviorValue, String behaviorUnit, Integer habitStatus) {
    this.runHabitId = runHabitId;
    this.userNickname = userNickname;
    this.identity = identity;
    this.runTime = runTime;
    this.place = place;
    this.behavior = behavior;
    this.behaviorValue = behaviorValue;
    this.behaviorUnit = behaviorUnit;
    this.habitStatus = habitStatus;
  }
  public HabitDayGetResponse(RunHabit runHabit, Integer habitStatus) {
    this.runHabitId = runHabit.getRunHabitId();
    this.userNickname = runHabit.getUser().getNickname();
    this.identity = runHabit.getIdentity();
    this.runTime = new TimeInfo(runHabit.getRunTime());
    this.place = runHabit.getPlace();
    this.behavior = runHabit.getAction();
    this.behaviorValue = runHabit.getValue();
    this.behaviorUnit = runHabit.getUnit();
    this.habitStatus = habitStatus;
  }
}
