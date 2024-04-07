package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * OAuth 관련 에러 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum OAuthErrorCode implements ErrorCode {

  /**
   * param
   */
  PARAM_SOCIAL_TYPE_EMPTY(HttpStatus.BAD_REQUEST, "소셜 타입은 필수값입니다."),
  PARAM_AUTH_CODE_EMPTY(HttpStatus.BAD_REQUEST, "Auth Code는 필수값입니다."),

  SOCIAL_TYPE_INVALID(HttpStatus.BAD_REQUEST, "유효하지 않은 소셜 타입입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
