package connectingstar.tars.common.exception.errorcode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
  NULL_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 값이 NULL입니다."),
  ILLEGAL_ARGUMENT_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 JWT 토큰입니다."),


  UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
  FORBIDDEN_USER(HttpStatus.FORBIDDEN, "해당 URL에 접근할 수 있는 권한이 존재하지 않습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
