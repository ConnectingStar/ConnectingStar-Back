package connectingstar.tars.oauth.request;

import lombok.Getter;

/**
 * 로그인 요청
 *
 * @author 송병선
 */
@Getter
public class OAuthLoginRequest {

  /**
   * 소셜 타입
   */
  private String socialType;
  /**
   * 인가 코드
   */
  private String authCode;
}
