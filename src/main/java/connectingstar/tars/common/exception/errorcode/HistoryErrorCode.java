package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HistoryErrorCode implements ErrorCode {
    HISTORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 기록을 찾을 수 없습니다."),
    HISTORY_CANNOT_ACCESS(HttpStatus.FORBIDDEN, "해당 기록을 조회할 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
