package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface RunHabitRepository extends JpaRepository<RunHabit,Integer> {

    List<RunHabit> findAllByUser(User user);

    /**
     * history.runDate가 입력한 runDate와 일치하는 값이 없는 RunHabit을 반환합니다.
     */
    @Query(
            "SELECT DISTINCT runHabit FROM RunHabit runHabit " +
            "WHERE NOT EXISTS (" +
            "SELECT habitHistory FROM HabitHistory habitHistory " +
            "WHERE habitHistory.runHabit = runHabit " +
                    "AND FUNCTION('DATE', habitHistory.runDate) = FUNCTION('DATE', :runDate)" +
            ")"
    )
    List<RunHabit> findAllByHistoryOfRunDateNotExist(@Param("runDate") LocalDate runDate);
}
