package connectingstar.tars.history.repository;

import connectingstar.tars.history.domain.HabitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HabitHistoryRepository extends JpaRepository<HabitHistory, Integer> {

    List<HabitHistory> findHabitHistoriesByUserIdAndRunDate(Integer userId, LocalDateTime runDate);
}
