package connectingstar.tars.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StarErrorCode implements ErrorCode {

  /**
   * empty
   */

  /**
   * not found
   */
  STAR_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원의 별을 찾을 수 없습니다."),
  STAR_ZERO_CNT(HttpStatus.BAD_REQUEST, "사용할 수 있는 별이 없습니다.");;

  /**
   * duplicate
   */


  private final HttpStatus httpStatus;
  private final String message;
}
