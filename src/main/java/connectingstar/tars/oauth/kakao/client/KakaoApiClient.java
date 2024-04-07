package connectingstar.tars.oauth.kakao.client;

import connectingstar.tars.oauth.kakao.KakaoOAuthConfig;
import connectingstar.tars.oauth.kakao.response.KakaoTokenResponse;
import connectingstar.tars.oauth.response.SocialUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Kakao Api 요청
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Component
public class KakaoApiClient {

  private final KakaoOAuthConfig kakaoOAuthConfig;

  /**
   * Kakao 엑세스 토큰 발급
   *
   * @param authCode 경도
   * @return Kakao 엑세스 토큰
   */
  public KakaoTokenResponse getToken(String authCode) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "authorization_code");
    params.add("client_id", kakaoOAuthConfig.clientId());
    params.add("redirect_uri", kakaoOAuthConfig.redirectUri());
    params.add("code", authCode);
    params.add("client_secret", kakaoOAuthConfig.clientSecret());

    String resultText = WebClient.create(kakaoOAuthConfig.tokenUri())
        .post()
        .bodyValue(params)
        .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        .exchangeToMono(res -> res.bodyToMono(String.class))
        .block();

    return makeTokenInfo(resultText);
  }

  public SocialUserResponse getUser(String bearerToken) {
    String resultText = WebClient.create(kakaoOAuthConfig.userInfoUri())
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
  private KakaoTokenResponse makeTokenInfo(String resultText) {
    try {
      JSONObject jsonObject = new JSONObject(resultText);
      return new KakaoTokenResponse(jsonObject.getString("token_type"),
          jsonObject.getString("access_token"),
          jsonObject.getInt("expires_in"), jsonObject.getString("refresh_token"),
          jsonObject.getInt("refresh_token_expires_in")
      );
    } catch (Exception e) {
      // TODO: 추후에 변경 예정
      throw new IllegalArgumentException("system.internal");
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
      return new SocialUserResponse(String.valueOf(jsonObject.getLong("id")));
    } catch (Exception e) {
      // TODO: 추후에 변경 예정
      throw new IllegalArgumentException("system.internal");
    }
  }
}
