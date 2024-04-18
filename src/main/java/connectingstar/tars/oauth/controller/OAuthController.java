package connectingstar.tars.oauth.controller;

import connectingstar.tars.common.config.JwtProperties;
import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.common.utils.CookieUtils;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.service.OAuthService;
import connectingstar.tars.oauth.validation.OAuthValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 비로그인 요청 api
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
@Slf4j
public class OAuthController {

  private final OAuthService oauthService;
  private final JwtProperties jwtProperties;

  /**
   * 소셜 타입에 따른 AuthCode 요청 Url 반환
   *
   * @param socialType 소셜 타입
   */
  @SneakyThrows
  @GetMapping(value = "/code/url")
  public void getAuthCodeUrl(@RequestParam(required = false) String socialType,
      HttpServletResponse response) {
    OAuthValidator.validate(socialType);

    response.sendRedirect(oauthService.getAuthCodeRequestUrl(SocialType.fromCode(socialType)));
  }

  /**
   * 로그아웃
   *
   * @param response 응답 객체
   */
  @GetMapping(value = "/logout")
  public ResponseEntity<?> getLogout(HttpServletResponse response) {
    CookieUtils.setCookie(jwtProperties.cookieName(), null, 0, response);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 소셜 로그인
   *
   * @param socialType 소셜 타입
   * @param authCode   AccessToken을 발급 받기 위한 코드
   */
  @PostMapping(value = "/login")
  public ResponseEntity<?> postLogin(@RequestParam(required = false) String socialType,
      @RequestParam(required = false) String authCode, HttpServletResponse response) {
    OAuthValidator.validate(socialType, authCode);

    CookieUtils.setCookie(jwtProperties.cookieName(),
        oauthService.login(SocialType.fromCode(socialType), authCode),
        24 * 60 * 60, response);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 소셜 로그인 해제
   */
  @DeleteMapping("/unlink/kakao")
  public ResponseEntity<?> unlinkKakao(@RequestParam(required = false) String accessToken) {
    log.info("accessToken = ", accessToken);
    oauthService.unlinkKaKao(accessToken);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
