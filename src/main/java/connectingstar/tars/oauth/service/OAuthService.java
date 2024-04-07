package connectingstar.tars.oauth.service;

import connectingstar.tars.oauth.domain.client.SocialUserClientComposite;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.domain.provider.AuthCodeRequestUrlProviderComposite;
import connectingstar.tars.oauth.response.SocialUserResponse;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OAuth 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class OAuthService {

  private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProvider;
  private final SocialUserClientComposite socialUserClient;

  private final UserRepository userRepository;

  /**
   * 소셜 타입에 따른 AuthCode 요청 Url 반환
   *
   * @param socialType 소셜 타입
   * @return AuthCode 요청 Url
   */
  public String getAuthCodeRequestUrl(SocialType socialType) {
    return authCodeRequestUrlProvider.provide(socialType);
  }

  /**
   * 소셜 로그인, 가입 전이라면 강제 회원가입
   *
   * @param socialType 소셜 타입
   * @param authCode   AccessToken을 발급 받기 위한 코드
   */
  @Transactional
  public void login(SocialType socialType, String authCode) {
    SocialUserResponse userInfo = socialUserClient.fetch(socialType, authCode);

    Optional<User> optionalUser = userRepository.findByEmail(userInfo.email());
    if (optionalUser.isEmpty()) {
      userRepository.save(new User(userInfo.email(), socialType));
    }
  }
}
