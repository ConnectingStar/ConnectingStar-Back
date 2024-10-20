package connectingstar.tars.user.repository;

import connectingstar.tars.user.domain.TokenRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRedisRepository extends CrudRepository<TokenRedis, String> {
        Optional<TokenRedis> findByAccessToken(String accessToken);

}
