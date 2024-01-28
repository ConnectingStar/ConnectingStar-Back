package connectingstar.tars.constellation.repository;

import connectingstar.tars.constellation.domain.ConstellationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstellationTypeRepository extends JpaRepository<ConstellationType, Integer> {
}
