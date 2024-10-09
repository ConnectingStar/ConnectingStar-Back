package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.RunHabitWithDevice;
import connectingstar.tars.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RunHabitRepository extends JpaRepository<RunHabit, Integer> {

    Optional<RunHabit> findByRunHabitId(Integer runHabitId);

    List<RunHabit> findAllByUser(User user);

    Integer countByUser(User user);

    /**
     * history.runDate가 입력한 runDate와 일치하는 값이 없는 RunHabit을 반환합니다.
     * Device 조인.
     */
    @Query(
            "SELECT new connectingstar.tars.habit.dto.RunHabitWithDevice(runHabit, device) FROM RunHabit runHabit " +
            "LEFT JOIN Device device ON runHabit.user = device.owningUser " +
            "WHERE NOT EXISTS (" +
            "SELECT habitHistory FROM HabitHistory habitHistory " +
            "WHERE habitHistory.runHabit = runHabit " +
                    "AND FUNCTION('DATE', habitHistory.runDate) = FUNCTION('DATE', :runDate)" +
            ")"
    )
    List<RunHabitWithDevice> findAllByHistoryOfRunDateNotExistWithDevice(@Param("runDate") LocalDate runDate);
}
