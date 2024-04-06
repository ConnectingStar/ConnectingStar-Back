package connectingstar.tars.oauth.domain.client;

import static connectingstar.tars.common.exception.errorcode.OAuthErrorCode.SOCIAL_TYPE_INVALID;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.oauth.response.SocialUserResponse;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Resource Server 회원 정보 요청
 *
 * @author 송병선
 */
@Component
public class SocialUserClientComposite {

  private final Map<SocialType, SocialUserClient> clientMap;

  public SocialUserClientComposite(Set<SocialUserClient> clients) {
    clientMap = clients.stream()
        .collect(toMap(
            SocialUserClient::supportSocial,
            identity()
        ));
  }

  /**
   * 소셜 타입에 맞는 회원 정보 반환
   *
   * @param socialType 소셜 타입
   * @return 회원 정보
   */
  public SocialUserResponse fetch(SocialType socialType, String authCode) {
    return getClient(socialType).fetch(authCode);
  }

  private SocialUserClient getClient(SocialType socialType) {
    return Optional.ofNullable(clientMap.get(socialType))
        .orElseThrow(() -> new ValidationException(SOCIAL_TYPE_INVALID));
  }
}
