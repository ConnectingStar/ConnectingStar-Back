package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.HabitHistory;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitHistoryRepository extends JpaRepository<HabitHistory,Integer> {

  List<HabitHistory> findHabitHistoriesByUserIdAndRunDate(Integer userId, LocalDateTime runDate);
}
