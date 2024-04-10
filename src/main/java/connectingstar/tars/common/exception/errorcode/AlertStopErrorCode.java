package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AlertStopErrorCode implements ErrorCode {
    /**
     * empty
     */
    ALERT_STOP_PARAM_ID_EMPTY(HttpStatus.BAD_REQUEST, "약속 전체 일시 ID는 필수 입력값입니다."),

    /**
     * not found
     */
    ALERT_STOP_NOT_FOUND(HttpStatus.BAD_REQUEST, "약속 전체 일시 정지 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
