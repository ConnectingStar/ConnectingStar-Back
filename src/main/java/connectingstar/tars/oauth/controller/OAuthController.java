package connectingstar.tars.oauth.controller;

import connectingstar.tars.auth.JwtService;
import connectingstar.tars.common.config.JwtProperties;
import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.common.utils.CookieUtils;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.request.OAuthLoginRequest;
import connectingstar.tars.oauth.service.OAuthService;
import connectingstar.tars.oauth.validation.OAuthValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  private final JwtService jwtService;

  /**
   * 소셜 타입에 따른 AuthCode 요청 Url 반환
   *
   * @param socialType 소셜 타입
   */
  @SneakyThrows
  @GetMapping(value = "/code/url")
  public void doGetCodeUrl(@RequestParam(required = false) String socialType, HttpServletResponse response) {
    OAuthValidator.validate(socialType);

    response.sendRedirect(oauthService.getAuthCodeRequestUrl(SocialType.fromCode(socialType)));
  }

  /**
   * 로그아웃
   *
   * @param response 응답 객체
   */
  @PostMapping(value = "/logout")
  public ResponseEntity<?> doPostLogout(HttpServletResponse response) {
    CookieUtils.setCookie(jwtProperties.cookieName(), null, 0, response);
    return ResponseEntity.ok(new SuccessResponse());
  }

  /**
   * 소셜 로그인
   *
   * @param param 요청 파라미터
   */
  @PostMapping(value = "/login")
  public ResponseEntity<?> doPostLogin(@RequestBody OAuthLoginRequest param, HttpServletResponse response) {
    OAuthValidator.validate(param);
    return ResponseEntity.ok(
        new DataResponse(oauthService.login(SocialType.fromCode(param.getSocialType()), param.getAuthCode(), response)));
  }

  /**
   * 토큰 재발급
   */
  @GetMapping(value = "/issue")
  public ResponseEntity<?> doPostIssue(HttpServletRequest request) {
    return ResponseEntity.ok(new DataResponse(jwtService.issueAccessToken(request)));
  }

  /**
   * 소셜 로그인 해제
   *
   * @deprecated
   * 탈퇴할 때 Unlink는 하지 않음
   * deletUser - DB에서 삭제하는 방식으로 구현
   */
  @DeleteMapping("/unlink/kakao")
  public ResponseEntity<?> unlinkKakao(@RequestParam(required = false) String accessToken) {
    oauthService.unlinkKaKao(accessToken);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
