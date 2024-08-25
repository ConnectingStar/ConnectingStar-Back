package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.QuitHabit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuitHabitRepository extends JpaRepository<QuitHabit, Integer> {
    Optional<QuitHabit> findByQuitHabitId(Integer quitHabitId);
}
