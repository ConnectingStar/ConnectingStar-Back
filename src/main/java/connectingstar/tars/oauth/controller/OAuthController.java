package connectingstar.tars.oauth.controller;

import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.service.OAuthService;
import connectingstar.tars.oauth.validation.OAuthValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
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
public class OAuthController {

  private final OAuthService oauthService;

  @SneakyThrows
  @GetMapping("/code/url")
  public void getAuthCodeUrl(@RequestParam(required = false) String socialType,
      HttpServletResponse response) {
    OAuthValidator.validate(socialType);

    response.sendRedirect(oauthService.getAuthCodeRequestUrl(SocialType.fromCode(socialType)));
  }

  /**
   * 소셜 로그인
   *
   * @param socialType 소셜 타입
   * @param authCode   AccessToken을 발급 받기 위한 코드
   */
  @PostMapping("/login")
  public ResponseEntity<?> postLogin(@RequestParam(required = false) String socialType,
      @RequestParam(required = false) String authCode) {
    OAuthValidator.validate(socialType, authCode);

    oauthService.login(SocialType.fromCode(socialType), authCode);
    return ResponseEntity.ok(new SuccessResponse());
  }
}
