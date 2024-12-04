package connectingstar.tars.oauth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Google OAuth 설정 정보
 *
 * @author 박정민
 */
@ConfigurationProperties(prefix = "oauth2.google")
public record GoogleOAuthConfig(

    String redirectUri,
    String clientId,
    String clientSecret,
    String[] scope,
    String authorizeUri,
    String tokenUri,
    String userInfoUri,
    String authorizationPrefix
) {

}
