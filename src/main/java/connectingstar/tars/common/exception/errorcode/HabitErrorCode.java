package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


/**
 * 습관 관련 에러 코드
 *
 * @author 김성수
 */

@Getter
@RequiredArgsConstructor
public enum HabitErrorCode implements ErrorCode {

    /**
     * empty
     */

    PARAM_ACHIEVEMENT_EMPTY(HttpStatus.BAD_REQUEST, "습관 달성도는 필수 입력값입니다"),
    PARAM_ACTION_EMPTY(HttpStatus.BAD_REQUEST, "습관 행동은 필수 입력값입니다"),
    PARAM_IDENTITY_EMPTY(HttpStatus.BAD_REQUEST, "습관 정체성은 필수 입력값입니다."),
    PARAM_IS_REST_EMPTY(HttpStatus.BAD_REQUEST, "오늘 휴식 여부는 필수 입력값입니다"),
    PARAM_PLACE_EMPTY(HttpStatus.BAD_REQUEST, "습관 장소는 필수 입력값입니다."),
    PARAM_RUN_TIME_EMPTY(HttpStatus.BAD_REQUEST, "습관 실천 시간은 필수 입력값입니다."),
    PARAM_RUN_HABIT_ID_EMPTY(HttpStatus.BAD_REQUEST, "진행중인 습관 ID(PK)는 필수 입력값입니다"),
    PARAM_REVIEW_EMPTY(HttpStatus.BAD_REQUEST, "별자취는 필수 입력값입니다"),
    PARAM_REFERENCE_DATE_EMPTY(HttpStatus.BAD_REQUEST, "참고 날짜는 필수 입력값입니다"),
    PARAM_DATE_EMPTY(HttpStatus.BAD_REQUEST, "날짜는 필수 입력값입니다"),
    PARAM_UNIT_EMPTY(HttpStatus.BAD_REQUEST, "습관 단위는 필수 입력값입니다"),
    PARAM_USER_ID_EMPTY(HttpStatus.BAD_REQUEST, "유저 ID(PK)는 필수 입력값입니다"),
    PARAM_VALUE_EMPTY(HttpStatus.BAD_REQUEST, "습관 수량(얼마나)은 필수 입력값입니다"),
    PARAM_INCREASE_EMPTY(HttpStatus.BAD_REQUEST, "오름차순 여부는 필수 입력값입니다."),

    /**
     * bad request
     */
    EXPIRED_DATE(HttpStatus.BAD_REQUEST, "해당 날짜에 습관기록을 작성할 수 없습니다."),
    ALREADY_CREATED_HABIT_HISTORY(HttpStatus.BAD_REQUEST, "이미 오늘 습관기록을 생성했습니다"),
    OUT_OF_ACHIEVEMENT_RANGE(HttpStatus.BAD_REQUEST, "입력가능한 달성도의 범위를 벗어났습니다(1~5 입력가능)"),
    NOT_USER_RUN_HABIT(HttpStatus.FORBIDDEN, "해당 유저의 진행중인 습관이 아닙니다."),


    /**
     * not found
     */

    ALERT_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "입력된 알람 차수를 찾을 수 없습니다"),
    ALERT_NOT_FOUND(HttpStatus.NOT_FOUND, "알람을 찾을 수 없습니다."),
    RUN_HABIT_NOT_FOUND(HttpStatus.NOT_FOUND, "진행중인 습관을 찾을 수 없습니다"),

    EXCEED_USER_MAX_COUNT(HttpStatus.UNPROCESSABLE_ENTITY, "유저가 가질 수 있는 최대 진행중인 습관 수를 초과했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}