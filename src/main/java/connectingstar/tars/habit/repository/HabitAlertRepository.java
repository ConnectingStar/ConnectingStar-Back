package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitAlertRepository extends JpaRepository<HabitAlert, Integer> {
    Optional<HabitAlert> findByRunHabitAndAlertOrder(RunHabit runHabit, Integer alertOrder);
}
