package connectingstar.tars.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Jwt 설정 정보
 *
 * @author 송병선
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(

    String secretKey,

    Long accessExpiration,

    String cookieName) {

}
