package connectingstar.tars.constellation.repository;

import connectingstar.tars.constellation.domain.ConstellationType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 별자리(캐릭터) 타입 Repository
 *
 * @author 송병선
 */
public interface ConstellationTypeRepository extends JpaRepository<ConstellationType, Integer> {
}
