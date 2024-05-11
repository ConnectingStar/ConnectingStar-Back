package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import connectingstar.tars.common.domain.TimeInfo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 내 종료한 습관 조회 응답
 *
 * @author 김성수
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"quitHabitId", "userId", "userNickname", "runTime", "place", "behavior", "behaviorValue", "behaviorUnit", "restValue", "reasonOfQuit", "startDate", "quitDate"})
public class QuitListResponse {

    /**
     * 종료한 습관 ID
     */
    private final Integer quitHabitId;

    /**
     * 사용자 PK
     */
    private final Integer userId;


    /**
     * 사용자 이름
     */
    private final String userNickname;

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
     * 실천횟수
     */
    private final Integer behaviorValue;

    /**
     * 단위
     */
    private final String behaviorUnit;

    /**
     * 휴식 실천횟수
     */
    private final Integer restValue;

    /**
     * 종료 사유
     */
    private final String reasonOfQuit;

    /**
     * 시작 날짜
     */
    private final LocalDateTime startDate;

    /**
     * 종료 날짜
     */
    private final LocalDateTime quitDate;

    public QuitListResponse(Integer quitHabitId, Integer userId, String userNickname, LocalTime runTime,
                            String place, String behavior, Integer behaviorValue, String behaviorUnit, Integer restValue, String reasonOfQuit,
                            LocalDateTime startDate, LocalDateTime quitDate) {
        this.quitHabitId = quitHabitId;
        this.userId = userId;
        this.userNickname = userNickname;
        this.runTime = new TimeInfo(runTime);
        this.place = place;
        this.behavior = behavior;
        this.behaviorValue = behaviorValue;
        this.behaviorUnit = behaviorUnit;
        this.restValue = restValue;
        this.reasonOfQuit = reasonOfQuit;
        this.startDate = startDate;
        this.quitDate = quitDate;
    }
}