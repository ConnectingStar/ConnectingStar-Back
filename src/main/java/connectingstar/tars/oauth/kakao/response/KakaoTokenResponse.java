package connectingstar.tars.oauth.kakao.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Kakao AccessToken 요청에 대한 응답 정보
 */
@JsonNaming(SnakeCaseStrategy.class)
public record KakaoTokenResponse(

    String tokenType,
    String accessToken,
    Integer expiresIn,
    String refreshToken,
    Integer refreshTokenExpiresIn
) {

  public KakaoTokenResponse(String tokenType, String accessToken, Integer expiresIn,
      String refreshToken, Integer refreshTokenExpiresIn) {
    this.tokenType = tokenType;
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
    this.refreshToken = refreshToken;
    this.refreshTokenExpiresIn = refreshTokenExpiresIn;
  }
}
