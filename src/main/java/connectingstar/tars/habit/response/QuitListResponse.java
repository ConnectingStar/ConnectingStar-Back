package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"quitHabitId", "userId","userNickname", "runTime", "place", "action", "value", "restValue", "reasonOfQuit","startDate", "quitDate"})
public class QuitListResponse {

    /**
     * 종료한 습관 ID
     */
    private final Integer quitHabitId;

    /**
     * 사용자 PK
     */
    private final Long userId;


    /**
     * 사용자 이름
     */
    private final String userNickname;

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
     * 실천횟수
     */
    private final Integer value;

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

    public QuitListResponse(Integer quitHabitId, Long userId, String userNickname, LocalTime runTime,
                                 String place, String action, Integer value, Integer restValue, String reasonOfQuit,
                                 LocalDateTime startDate, LocalDateTime quitDate) {
        this.quitHabitId = quitHabitId;
        this.userId = userId;
        this.userNickname = userNickname;
        this.runTime = runTime;
        this.place = place;
        this.action = action;
        this.value = value;
        this.restValue = restValue;
        this.reasonOfQuit = reasonOfQuit;
        this.startDate = startDate;
        this.quitDate = quitDate;
    }
}