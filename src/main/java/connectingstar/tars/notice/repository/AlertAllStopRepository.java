package connectingstar.tars.notice.repository;

import connectingstar.tars.notice.domain.AlertAllStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertAllStopRepository extends JpaRepository<AlertAllStop, Integer> {
}
