package connectingstar.tars.user.validation;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_PARAM_ID_EMPTY;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.PARAM_ACTION_EMPTY;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.PARAM_IDENTITY_EMPTY;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.PARAM_PLACE_EMPTY;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.PARAM_RUN_TIME_EMPTY;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.PARAM_UNIT_EMPTY;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.PARAM_VALUE_EMPTY;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_AGE_RANGE_TYPE_EMPTY;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_AGE_RANGE_TYPE_INVALID;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_GENDER_TYPE_EMPTY;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_GENDER_TYPE_INVALID;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_IDENTITY_EMPTY;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_PARAM_NICKNAME_EMPTY;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.user.domain.enums.AgeRangeType;
import connectingstar.tars.user.domain.enums.GenderType;
import connectingstar.tars.user.request.UserAgeRangeRequest;
import connectingstar.tars.user.request.UserConstellationRequest;
import connectingstar.tars.user.request.UserGenderRequest;
import connectingstar.tars.user.request.UserIdentityRequest;
import connectingstar.tars.user.request.UserNicknameRequest;
import connectingstar.tars.user.request.UserOnboardingRequest;
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
   * 회원 나이대 수정 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserAgeRangeRequest param) {
    validateEmpty(param.getAgeRangeType(), USER_PARAM_AGE_RANGE_TYPE_EMPTY);

    if (!AgeRangeType.containCode(param.getAgeRangeType())) {
      throw new ValidationException(USER_PARAM_AGE_RANGE_TYPE_INVALID);
    }
  }

  /**
   * 회원 온보딩 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserOnboardingRequest param) {
    validateEmpty(param.getNickname(), USER_PARAM_NICKNAME_EMPTY);
    validateEmpty(param.getAgeRangeType(), USER_PARAM_AGE_RANGE_TYPE_EMPTY);
    validateEmpty(param.getGenderType(), USER_PARAM_GENDER_TYPE_EMPTY);
    validateNull(param.getIdentity(), PARAM_IDENTITY_EMPTY);
    validateNull(param.getRunTime(), PARAM_RUN_TIME_EMPTY);
    validateNull(param.getPlace(), PARAM_PLACE_EMPTY);
    validateNull(param.getBehavior(), PARAM_ACTION_EMPTY);
    validateNull(param.getBehaviorValue(), PARAM_VALUE_EMPTY);
    validateNull(param.getBehaviorUnit(), PARAM_UNIT_EMPTY);

    if (!AgeRangeType.containCode(param.getAgeRangeType())) {
      throw new ValidationException(USER_PARAM_AGE_RANGE_TYPE_INVALID);
    }
    if (!GenderType.containCode(param.getGenderType())) {
      throw new ValidationException(USER_PARAM_GENDER_TYPE_INVALID);
    }
  }

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
   * 회원 정체성 수정 유효성 체크
   *
   * @param param 요청 파라미터
   */
  public void validate(UserIdentityRequest param) {
    validateEmpty(param.getIdentity(), USER_PARAM_IDENTITY_EMPTY);
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
