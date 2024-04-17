package connectingstar.tars.user.validation;

import java.util.Objects;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.user.request.UserConstellationCreateRequest;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import connectingstar.tars.user.request.UserProfileConstellationRequest;
import lombok.experimental.UtilityClass;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_PARAM_ID_EMPTY;

/**
 * 회원 요청 파라미터 검증
 *
 * @author 송병선
 */
@UtilityClass
public class UserValidator {

  /**
   * 진행중인 별자리 별 등록 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserConstellationStarRequest param) {
    validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
  }

  /**
   * 별자리 선택 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserConstellationCreateRequest param) {
    validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
  }

  /**
   * 프로필 별자리 수정 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserProfileConstellationRequest param) {
    validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
  }

  private void validateNull(Object param, ErrorCode errorCode) {
    if (Objects.isNull(param)) {
      throw new ValidationException(errorCode);
    }
  }
}
