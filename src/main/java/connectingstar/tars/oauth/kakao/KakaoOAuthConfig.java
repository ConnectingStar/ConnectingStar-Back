package connectingstar.tars.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Kakao OAuth 설정 정보
 *
 * @author 송병선
 */
@ConfigurationProperties(prefix = "oauth2.kakao")
public record KakaoOAuthConfig(

    String redirectUri,
    String clientId,
    String clientSecret,
    String[] scope,
    String authorizeUri,
    String tokenUri,
    String userInfoUri,
    String authorizationHeader
) {

}
