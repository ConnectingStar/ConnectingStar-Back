package connectingstar.tars.star.repository;

import connectingstar.tars.star.domain.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface startRepository extends JpaRepository<Star, Long> {
}
