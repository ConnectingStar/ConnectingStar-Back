package connectingstar.tars.oauth.google;

import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.domain.provider.AuthCodeRequestUrlProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 카카오 Google 요청 Url 제공
 *
 * @author 박정민
 */
@Component
@RequiredArgsConstructor
public class GoogleAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

    private final GoogleOAuthConfig googleOAuthConfig;

    @Override
    public SocialType supportSocial() {
        return SocialType.GOOGLE;
    }

    @Override
    public String provideUrl() {
        return UriComponentsBuilder
                .fromUriString(googleOAuthConfig.authorizeUri())
                .queryParam("response_type", "code")
                .queryParam("client_id", googleOAuthConfig.clientId())
                .queryParam("redirect_uri", googleOAuthConfig.redirectUri())
                .queryParam("scope", String.join(" ", googleOAuthConfig.scope()))
                .toUriString();
    }
}

