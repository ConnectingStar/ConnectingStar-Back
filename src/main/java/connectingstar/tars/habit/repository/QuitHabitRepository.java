package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.QuitHabit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuitHabitRepository extends JpaRepository<QuitHabit,Integer> {
}
