package connectingstar.tars.auth.response;

import lombok.Getter;

/**
 * 엑세스 토큰 정보 반환
 *
 * @author 송병선
 */
@Getter
public class TokenResponse {

  /**
   * 엑세스 토큰
   */
  private final String accessToken;

  public TokenResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
