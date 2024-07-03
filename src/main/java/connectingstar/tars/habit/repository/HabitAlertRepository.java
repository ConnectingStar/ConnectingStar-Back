package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.HabitAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface HabitAlertRepository extends JpaRepository<HabitAlert,Integer> {
    List<HabitAlert> findByAlertTimeBetween(LocalTime startTime, LocalTime endTime);
}
