package connectingstar.tars.user.validation;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_PARAM_ID_EMPTY;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.user.request.UserConstellationCreateRequest;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import java.util.Objects;
import lombok.experimental.UtilityClass;

/**
 * 회원 요청 파라미터 검증
 *
 * @author 송병선
 */
@UtilityClass
public class UserValidator {

  /**
   * 진행중인 별자리 별 등록 요청 검증
   */
  public void validate(UserConstellationStarRequest param) {
    validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
  }

  /**
   * 별자리 선택 요청 검증
   */
  public void validate(UserConstellationCreateRequest param) {
    validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
  }

  private void validateNull(Object param, ErrorCode errorCode) {
    if (Objects.isNull(param)) {
      throw new ValidationException(errorCode);
    }
  }
}
