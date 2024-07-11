package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunHabitRepository extends JpaRepository<RunHabit,Integer> {

    List<RunHabit> findAllByUser(User user);
}
