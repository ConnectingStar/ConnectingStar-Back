package connectingstar.tars.constellation.repository;

import connectingstar.tars.constellation.domain.Constellation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstellationRepository extends JpaRepository<Constellation, Integer> {
}
