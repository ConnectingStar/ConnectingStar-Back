package connectingstar.tars.constellation.repository;

import connectingstar.tars.constellation.domain.Constellation;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 별자리(캐릭터) JPA Repository
 *
 * @author 송병선
 */
public interface ConstellationRepository extends JpaRepository<Constellation, Integer> {

}
