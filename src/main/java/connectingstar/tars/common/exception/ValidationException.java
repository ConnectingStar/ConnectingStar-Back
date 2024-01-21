package connectingstar.tars.common.exception;

import connectingstar.tars.common.exception.errorcode.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * 유효성 검사 에러
 *
 * @author 송병선
 */
@Getter
@ToString
@RequiredArgsConstructor
public class ValidationException extends RuntimeException{

    private final ErrorCode errorCode;
}
