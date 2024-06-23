package connectingstar.tars.oauth.service;

import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.INVALID_TOKEN;

import connectingstar.tars.auth.JwtService;
import connectingstar.tars.common.config.JwtProperties;
import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.CookieUtils;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.oauth.domain.client.SocialUserClientComposite;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.domain.provider.AuthCodeRequestUrlProviderComposite;
import connectingstar.tars.oauth.response.OAuthLoginResponse;
import connectingstar.tars.oauth.response.SocialUserResponse;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

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
  private final JwtProperties jwtProperties;
  private final JwtService jwtService;
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
   * @param authCode   refreshToken 발급 받기 위한 코드
   */
  @Transactional
  public OAuthLoginResponse login(SocialType socialType, String authCode, HttpServletResponse response) {
    SocialUserResponse userInfo = socialUserClient.fetch(socialType, authCode);

    Optional<User> user = userRepository.findByEmail(userInfo.email());
    if (user.isEmpty()) {
      user = Optional.of(userRepository.save(new User(userInfo.email(), socialType)));
    }

    String accessToken = jwtService.generateAccessToken(user.get());
    String refreshToken = jwtService.generateRefreshToken(user.get());

    //리프레시 토큰은 쿠키에 저장 (24 * 60 * 60 * 7) = 7일
    CookieUtils.setCookie(jwtProperties.cookieName(), refreshToken, 24 * 60 * 60 * 7, response);
    SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(refreshToken));

    return new OAuthLoginResponse(UserUtils.getUser().getOnboard(), accessToken);
  }

  /**
   * 소셜 로그인 연동 해제
   */
  @Transactional
  public void unlinkKaKao(String accessToken) {
    try {
      String kakaoUnlinkUrl = "https://kapi.kakao.com/v1/user/unlink";
      WebClient webClient = WebClient.builder().build();

      String response = webClient
          .post()   //POST 요청을 보냄
          .uri(kakaoUnlinkUrl)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
          .retrieve()
          .bodyToMono(String.class)   //응답본문을 String으로 변환
          .block(); //비동기 요청을 동기적으로 처리

      System.out.println(">>>>>>>>>>>> response = " + response);
    } catch (Exception e) {
      throw new ValidationException(INVALID_TOKEN);
    }
  }
}
