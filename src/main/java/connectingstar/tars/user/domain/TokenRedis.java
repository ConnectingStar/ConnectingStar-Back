package connectingstar.tars.user.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 7) // 리프레시토큰과 expiretime 일치
public class TokenRedis {

    @Id
    private String id;

    @Indexed
    private String accessToken;

    private String refreshToken;

    public TokenRedis(String id, String accessToken, String refreshToken){
        this.id= id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

}
