package connectingstar.tars.common.handler;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.common.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        log.info("Exception: " + ex.getClass().getSimpleName() + "(" + ex.getErrorCode().getMessage() + ")");

        return getErrorResponse(ex.getErrorCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.info("Exception: " + ex.getClass().getSimpleName() + "(" + ex.getMessage() + ")");

        return getErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Request Parameter 유효성 검사 실패 처리
     * <p>
     * jakarta @Valid
     * enum Converter
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("Exception: " + exception.getClass().getSimpleName() + "(" + exception.getMessage() + ")");

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        StringBuilder errorMessageBuilder = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMessageBuilder.append("유효하지 않은 값이 입력되었습니다: ( 필드: "
                    + ObjectUtils.nullSafeToString(fieldError.getField()) +
                    ", 값: "
                    + ObjectUtils.nullSafeToString(fieldError.getRejectedValue()) + " )\n");
        }

        return getErrorResponse(httpStatus, errorMessageBuilder.toString());
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getMessage()));
    }

    private ResponseEntity<Object> getErrorResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus)
                .body(new ErrorResponse(httpStatus.value(), message));
    }
}
