package connectingstar.tars.oauth.google.client;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.oauth.google.GoogleOAuthConfig;
import connectingstar.tars.oauth.google.response.GoogleTokenResponse;
import connectingstar.tars.oauth.response.SocialUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static connectingstar.tars.common.exception.errorcode.OAuthErrorCode.OAUTH_TOKEN_FAIL;
import static connectingstar.tars.common.exception.errorcode.OAuthErrorCode.OAUTH_USER_FAIL;

/**
 * Google Api 요청
 *
 * @author 박정민
 */
@RequiredArgsConstructor
@Component
public class GoogleApiClient {

  private final GoogleOAuthConfig googleOAuthConfig;

  /**
   * Google 엑세스 토큰 발급
   *
   * @param authCode 경도
   * @return Google 엑세스 토큰
   */
  public GoogleTokenResponse getToken(String authCode) {
    String decode = URLDecoder.decode(authCode, StandardCharsets.UTF_8);
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", googleOAuthConfig.clientId());
    params.add("redirect_uri", googleOAuthConfig.redirectUri());
    params.add("code", decode);
    params.add("client_secret", googleOAuthConfig.clientSecret());

    String resultText = WebClient.create(googleOAuthConfig.tokenUri())
        .post()
        .bodyValue(params)
        .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        .exchangeToMono(res -> {
          System.out.println("Response status: " + res.statusCode());
          return res.bodyToMono(String.class);
        })
        .block();

    return makeTokenInfo(resultText);
  }

  public SocialUserResponse getUser(String bearerToken) {
    String resultText = WebClient.create(googleOAuthConfig.userInfoUri())
        .get()
        .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        .header(HttpHeaders.AUTHORIZATION, bearerToken)
        .exchangeToMono(res -> res.bodyToMono(String.class))
        .block();


    return makeUserInfo(resultText);
  }

  /**
   * json 문자열 파싱 - 토큰
   *
   * @param resultText 응답 문자열
   * @return 토큰 정보
   */
  private GoogleTokenResponse makeTokenInfo(String resultText) {
    try {
      JSONObject jsonObject = new JSONObject(resultText);
      return new GoogleTokenResponse(jsonObject.getString("token_type"),
          jsonObject.getString("access_token"),
          jsonObject.getInt("expires_in"),
//              jsonObject.getString("refresh_token"),
          jsonObject.getString("id_token")
      );
    } catch (Exception e) {
      throw new ValidationException(OAUTH_TOKEN_FAIL);
    }
  }

  /**
   * json 문자열 파싱 - 회원 정보
   *
   * @param resultText 응답 문자열
   * @return 회원 정보
   */
  private SocialUserResponse makeUserInfo(String resultText) {
    try {
      JSONObject jsonObject = new JSONObject(resultText);
      return new SocialUserResponse(String.valueOf(jsonObject.getString("email")),
              String.valueOf(jsonObject.get("name")),
              String.valueOf(jsonObject.get("picture"))
              );
    } catch (Exception e) {
      throw new ValidationException(OAUTH_USER_FAIL);
    }
  }
}
