package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.HabitAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitAlertRepository extends JpaRepository<HabitAlert,Integer> {
}
