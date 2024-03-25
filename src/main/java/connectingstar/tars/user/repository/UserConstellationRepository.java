package connectingstar.tars.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import connectingstar.tars.user.domain.UserConstellation;

/**
 * 회원 별자리(캐릭터) Repository
 *
 * @author 송병선
 */
public interface UserConstellationRepository extends JpaRepository<UserConstellation, Integer> {

  boolean existsByUser_IdAndConstellation_ConstellationId(Integer userId, Integer constellationId);
}
