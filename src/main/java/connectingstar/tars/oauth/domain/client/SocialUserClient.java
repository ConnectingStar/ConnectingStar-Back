package connectingstar.tars.oauth.domain.client;

import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.response.SocialUserResponse;

/**
 * Resource Server 회원 정보 요청
 *
 * @author 송병선
 */
public interface SocialUserClient {

  /**
   * @return 지원하는 SocialType
   */
  SocialType supportSocial();

  /**
   * @return 회원 정보
   */
  SocialUserResponse fetch(String authCode);
}
