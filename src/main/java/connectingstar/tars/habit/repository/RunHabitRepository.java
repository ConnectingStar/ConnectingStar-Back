package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunHabitRepository extends JpaRepository<RunHabit, Integer> {

    Optional<RunHabit> findByRunHabitId(Integer runHabitId);

    List<RunHabit> findAllByUser(User user);

    Integer countByUser(User user);
}
