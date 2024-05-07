package connectingstar.tars.oauth.response;

import lombok.Getter;

/**
 * 로그인 결과 반환
 *
 * @author 송병선
 */
@Getter
public class OAuthLoginResponse {

  /**
   * 온보딩 통과 여부
   */
  private final Boolean onboard;

  public OAuthLoginResponse(Boolean onboard) {
    this.onboard = onboard;
  }
}
