package connectingstar.tars.oauth.domain.provider;

import connectingstar.tars.oauth.domain.enums.SocialType;

/**
 * AuthCode 요청 Url 제공
 *
 * @author 송병선
 */
public interface AuthCodeRequestUrlProvider {

  /**
   * @return 지원하는 SocialType
   */
  SocialType supportSocial();

  /**
   * @return 로그인 url 반환
   */
  String provideUrl();
}
