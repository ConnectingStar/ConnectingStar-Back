package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


/**
 * 습관 관련 에러 코드
 *
 * @author 송병선
 */

@Getter
@RequiredArgsConstructor
public enum HabitErrorCode implements ErrorCode {

    /**
     * empty
     */
    RUN_HABIT_PARAM_IDENTITY_EMPTY(HttpStatus.BAD_REQUEST, "진행중인 습관 정체성은 필수 입력값입니다."),
    RUN_HABIT_PARAM_RUN_TIME_EMPTY(HttpStatus.BAD_REQUEST, "진행중인 습관 실천 시간은 필수 입력값입니다."),
    RUN_HABIT_PARAM_PLACE_EMPTY(HttpStatus.BAD_REQUEST, "진행중인 습관 장소는 필수 입력값입니다."),
    RUN_HABIT_PARAM_ACTION_EMPTY(HttpStatus.BAD_REQUEST, "진행중인 습관 행동은 필수 입력값입니다"),
    RUN_HABIT_PARAM_VALUE_EMPTY(HttpStatus.BAD_REQUEST, "진행중인 습관 수량(얼마나)은 필수 입력값입니다"),
    RUN_HABIT_PARAM_UNIT_EMPTY(HttpStatus.BAD_REQUEST, "진행중인 습관 단위는 필수 입력값입니다"),

    /**
     * not found
     */
    ALERT_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "입력된 알람 차수를 찾을 수 없습니다"),
    ALERT_NOT_FOUND(HttpStatus.NOT_FOUND,"알람을 찾을 수 없습니다."),
    RUN_HABIT_NOT_FOUND(HttpStatus.NOT_FOUND, "진행중인 습관을 찾을 수 없습니다");
    private final HttpStatus httpStatus;
    private final String message;
}
