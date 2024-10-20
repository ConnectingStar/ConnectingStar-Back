package connectingstar.tars.oauth.service;

import connectingstar.tars.user.domain.TokenRedis;
import connectingstar.tars.user.repository.TokenRedisRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private final TokenRedisRepository tokenRedisRepository;

    @Transactional
    public void saveToken(String id ,String accessToken, String refreshToken){

        tokenRedisRepository.save(new TokenRedis(id, accessToken, refreshToken));
    }

    public TokenRedis findRefreshToken(String token){

        TokenRedis tokenObject = tokenRedisRepository.findByAccessToken(token)
                .orElse(new TokenRedis());

        return tokenObject;
    }
    public void updateToken(String newAcessToken, TokenRedis tokenRedis){
        tokenRedis.setAccessToken(newAcessToken);
        tokenRedisRepository.save(tokenRedis);
    }

    public void deleteToken(TokenRedis tokenRedis){
        tokenRedisRepository.delete(tokenRedis);
    }
}
