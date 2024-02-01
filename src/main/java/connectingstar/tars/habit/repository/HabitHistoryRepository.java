package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.HabitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitHistoryRepository extends JpaRepository<HabitHistory,Integer> {
}
