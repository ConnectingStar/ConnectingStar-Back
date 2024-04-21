package connectingstar.tars.user.validation;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_PARAM_ID_EMPTY;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_GENDER_TYPE_EMPTY;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_GENDER_TYPE_INVALID;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_NICKNAME_EMPTY;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.user.domain.enums.GenderType;
import connectingstar.tars.user.request.UserConstellationRequest;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import connectingstar.tars.user.request.UserGenderRequest;
import connectingstar.tars.user.request.UserNicknameRequest;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * 회원 요청 파라미터 검증
 *
 * @author 송병선
 */
@UtilityClass
public class UserValidator {

  /**
   * 회원 성별 수정 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserGenderRequest param) {
    validateEmpty(param.getGenderType(), USER_PARAM_GENDER_TYPE_EMPTY);

    if (!GenderType.containCode(param.getGenderType())) {
      throw new ValidationException(USER_PARAM_GENDER_TYPE_INVALID);
    }
  }

  /**
   * 회원 닉네임 수정 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserNicknameRequest param) {
    validateEmpty(param.getNickname(), USER_PARAM_NICKNAME_EMPTY);
  }

  /**
   * 진행중인 별자리 별 등록 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserConstellationStarRequest param) {
    validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
  }

  /**
   * 별자리 선택(해금 시작), 프로필 별자리 수정 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserConstellationRequest param) {
    validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
  }

  private void validateNull(Object param, ErrorCode errorCode) {
    if (Objects.isNull(param)) {
      throw new ValidationException(errorCode);
    }
  }

  private void validateEmpty(String param, ErrorCode errorCode) {
    if (!StringUtils.hasText(param)) {
      throw new ValidationException(errorCode);
    }
  }
}
