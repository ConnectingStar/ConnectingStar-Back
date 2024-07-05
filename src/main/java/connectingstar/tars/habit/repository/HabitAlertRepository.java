package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.dto.HabitAlertWithDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface HabitAlertRepository extends JpaRepository<HabitAlert,Integer> {
    /**
     * 1. alertTime BETWEEN 조건으로 조회.
     * 2. User table JOIN.
     * 3. RunHabit table JOIN.
     * 4. device table을 habitAlert.user_id = device.owning_user_id 조건으로 LEFT OUTER JOIN
     *
     * @author 이우진
     */
    @Query("SELECT new connectingstar.tars.habit.dto.HabitAlertWithDevice(ha, d) FROM HabitAlert ha " +
            "LEFT JOIN FETCH ha.user " +
            "LEFT JOIN FETCH ha.runHabit " +
            "LEFT JOIN Device d ON ha.user = d.owningUser " +
            "WHERE ha.alertTime BETWEEN :startTime AND :endTime")
    List<HabitAlertWithDevice> findByAlertTimeBetweenWithUserAndRunHabitAndDevice(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
}
