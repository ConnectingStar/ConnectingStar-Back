package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.RunHabit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunHabitRepository extends JpaRepository<RunHabit,Integer> {
}
