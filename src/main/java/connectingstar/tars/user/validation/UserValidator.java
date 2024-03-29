package connectingstar.tars.user.validation;

import java.util.Objects;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import lombok.experimental.UtilityClass;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_PARAM_ID_EMPTY;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_ID_EMPTY;

/**
 * 회원 요청 파라미터 검증
 *
 * @author 송병선
 */
@UtilityClass
public class UserValidator {
  /**
   * userId 검증
   */
  public void validate(Integer userId) {
    validateNull(userId, USER_PARAM_ID_EMPTY);
  }

  /**
   * 회원 별자리(캐릭터) 등록 요청 검증
   */
  public void validate(UserConstellationStarRequest param) {
    validateNull(param.getUserId(), USER_PARAM_ID_EMPTY);
    validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
  }

  private void validateNull(Object param, ErrorCode errorCode) {
    if (Objects.isNull(param)) {
      throw new ValidationException(errorCode);
    }
  }
}
