package connectingstar.tars.oauth.google.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Google AccessToken 요청에 대한 응답 정보
 */
@JsonNaming(SnakeCaseStrategy.class)
public record GoogleTokenResponse(

    String tokenType,
    String accessToken,
    Integer expiresIn,
    String id_token
) {

  public GoogleTokenResponse(String tokenType, String accessToken, Integer expiresIn,
                             String id_token) {
    this.tokenType = tokenType;
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
    this.id_token = id_token;
  }
}
