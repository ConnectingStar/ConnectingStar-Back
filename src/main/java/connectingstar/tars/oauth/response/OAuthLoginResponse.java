package connectingstar.tars.oauth.response;

import lombok.Getter;

/**
 * 로그인 결과 반환
 *
 * @author 송병선
 */
@Getter
public class OAuthLoginResponse {

  public OAuthLoginResponse(Boolean onboard, String accessToken) {
    this.onboard = onboard;
    this.accessToken = accessToken;
  }
  /**
   * 엑세스 토큰
   */
  private final String accessToken;
  /**
   * 온보딩 통과 여부
   */
  private final Boolean onboard;
}
