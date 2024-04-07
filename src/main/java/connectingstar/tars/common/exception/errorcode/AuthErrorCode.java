package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Auth 관련 에러 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 서명입니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
  UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 JWT 토큰입니다."),
  ILLEGAL_ARGUMENT_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 JWT 토큰입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
