package connectingstar.tars.oauth.validation;

import static connectingstar.tars.common.exception.errorcode.OAuthErrorCode.PARAM_AUTH_CODE_EMPTY;
import static connectingstar.tars.common.exception.errorcode.OAuthErrorCode.PARAM_SOCIAL_TYPE_EMPTY;
import static connectingstar.tars.common.exception.errorcode.OAuthErrorCode.SOCIAL_TYPE_INVALID;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.oauth.domain.enums.SocialType;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * OAuth 요청 파라미터 검증
 *
 * @author 송병선
 */
@UtilityClass
public class OAuthValidator {

  /**
   * 소셜 로그인 요청 유효성 체크
   *
   * @param socialType 소셜 타입
   * @param authCode   AccessToken을 발급 받기 위한 코드
   */
  public void validate(String socialType, String authCode) {
    validate(socialType);

    validateEmpty(authCode, PARAM_AUTH_CODE_EMPTY);
  }

  /**
   * 소셜 타입 유효성 체크
   *
   * @param socialType 소셜 타입
   */
  public void validate(String socialType) {
    validateEmpty(socialType, PARAM_SOCIAL_TYPE_EMPTY);
    if (!SocialType.containCode(socialType)) {
      throw new ValidationException(SOCIAL_TYPE_INVALID);
    }
  }

  private void validateEmpty(String str, ErrorCode errorCode) {
    if (!StringUtils.hasText(str)) {
      throw new ValidationException(errorCode);
    }
  }
}
