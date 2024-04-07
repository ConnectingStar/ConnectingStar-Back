package connectingstar.tars.oauth.kakao.client;

import connectingstar.tars.oauth.domain.client.SocialUserClient;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.kakao.KakaoOAuthConfig;
import connectingstar.tars.oauth.kakao.response.KakaoTokenResponse;
import connectingstar.tars.oauth.response.SocialUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Kakao 회원 정보 요청
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Component
public class KakaoUserClient implements SocialUserClient {

  private final KakaoApiClient kakaoApiClient;
  private final KakaoOAuthConfig kakaoOAuthConfig;

  @Override
  public SocialType supportSocial() {
    return SocialType.KAKAO;
  }

  @Override
  public SocialUserResponse fetch(String authCode) {
    KakaoTokenResponse tokenInfo = kakaoApiClient.getToken(authCode);
    return kakaoApiClient.getUser(
        kakaoOAuthConfig.authorizationHeader() + tokenInfo.accessToken());
  }
}
