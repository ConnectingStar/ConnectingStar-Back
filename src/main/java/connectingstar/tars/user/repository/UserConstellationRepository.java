package connectingstar.tars.user.repository;

import connectingstar.tars.user.domain.UserConstellation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 별자리(캐릭터) Repository
 *
 * @author 송병선
 */
public interface UserConstellationRepository extends JpaRepository<UserConstellation, Integer> {

    Boolean existsByUser_IdAndConstellation_ConstellationId(Integer userId, Integer constellationId);

    Optional<UserConstellation> findByUserConstellationId(Integer userConstellationId);
}
