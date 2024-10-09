package connectingstar.tars.constellation.repository;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.enums.ConstellationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 별자리(캐릭터) JPA Repository
 *
 * @author 송병선
 */
public interface ConstellationRepository extends JpaRepository<Constellation, Integer> {
    Optional<Constellation> findByCode(ConstellationCode code);
}
