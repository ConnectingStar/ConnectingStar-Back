package connectingstar.tars.constellation.repository;

import connectingstar.tars.constellation.domain.Constellation;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstellationRepository extends JpaRepository<Constellation, Integer> {

    List<Constellation> findAllByConstellationType_ConstellationTypeId(Integer constellationTypeId);

    boolean existsByConstellationType_ConstellationTypeId(Integer constellationTypeId);
}
