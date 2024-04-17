package connectingstar.tars.common.exception.errorcode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 관련 에러 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

  /**
   * empty
   */
  USER_PARAM_ID_EMPTY(HttpStatus.BAD_REQUEST, "회원 ID는 필수 입력값입니다."),

  /**
   * not found
   */
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원 정보를 찾을 수 없습니다."),

  /**
   * duplicate
   */
  USER_CONSTELLATION_DUPLICATE(HttpStatus.BAD_REQUEST, "이미 등록된 별자리입니다."),
  USER_CONSTELLATION_ALREADY_PROGRESS(HttpStatus.BAD_REQUEST, "이미 진행중인 별자리가 존재합니다."),
  USER_CONSTELLATION_NOT_PROGRESS(HttpStatus.BAD_REQUEST, "진행중인 별자리가 아닙니다."),
  USER_CONSTELLATION_NOT_REGISTER(HttpStatus.BAD_REQUEST, "해당 별자리를 보유하고 있지 않습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
