package connectingstar.tars.oauth.google.client;

import connectingstar.tars.oauth.domain.client.SocialUserClient;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.google.GoogleOAuthConfig;
import connectingstar.tars.oauth.google.response.GoogleTokenResponse;
import connectingstar.tars.oauth.response.SocialUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Google 회원 정보 요청
 *
 * @author 박정민
 */
@RequiredArgsConstructor
@Component
public class GoogleUserClient implements SocialUserClient {

  private final GoogleApiClient googleApiClient;
  private final GoogleOAuthConfig googleOAuthConfig;

  @Override
  public SocialType supportSocial() {
    return SocialType.GOOGLE;
  }

  @Override
  public SocialUserResponse fetch(String authCode) {
    GoogleTokenResponse tokenInfo = googleApiClient.getToken(authCode);
    return googleApiClient.getUser(
            googleOAuthConfig.authorizationPrefix() + tokenInfo.accessToken());
  }
}
