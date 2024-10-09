package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.dto.HabitAlertWithDevice;
import connectingstar.tars.habit.domain.RunHabit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import java.time.LocalTime;
import java.util.List;

public interface HabitAlertRepository extends JpaRepository<HabitAlert,Integer> {
    Optional<HabitAlert> findByRunHabitAndAlertOrder(RunHabit runHabit, Integer alertOrder);

    void deleteInBatchByRunHabit(RunHabit runHabit);

    /**
     * 1. status = 1 (active),
     * 2. alertTime BETWEEN 조건으로 조회.
     * 3. User table JOIN.
     * 4. RunHabit table JOIN.
     * 5. device table을 habitAlert.user_id = device.owning_user_id 조건으로 LEFT OUTER JOIN
     *
     * @author 이우진
     */
    @Query("SELECT new connectingstar.tars.habit.dto.HabitAlertWithDevice(ha, d) FROM HabitAlert ha " +
            "LEFT JOIN FETCH ha.user " +
            "LEFT JOIN FETCH ha.runHabit " +
            "LEFT JOIN Device d ON ha.user = d.owningUser " +
            "WHERE ha.alertStatus = TRUE " +
            "AND ha.alertTime BETWEEN :startTime AND :endTime")
    List<HabitAlertWithDevice> findActiveByAlertTimeBetweenWithUserAndRunHabitAndDevice(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
}
