package connectingstar.tars.notice.repository;

import connectingstar.tars.notice.domain.AlertStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertStopRepository extends JpaRepository<AlertStop, Integer> {
}
