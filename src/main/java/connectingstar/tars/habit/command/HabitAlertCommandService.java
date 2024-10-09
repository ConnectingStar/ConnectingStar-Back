package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.ALERT_NOT_FOUND;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.ALERT_ORDER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class HabitAlertCommandService {

    public static final int FIRST_ALERT_STATUS = 1;
    public static final boolean ALERT_ON = true;
    public static final int FIRST_ALERT_DEFAULT = 10;
    public static final int SECOND_ALERT_STATUS = 2;
    public static final int SECOND_ALERT_DEFAULT = 30;

    private final HabitAlertQueryService habitAlertQueryService;

    public HabitAlert makeAlert(RunHabit runHabit, LocalTime runTime, LocalTime alert, int alertStatus) {
        HabitAlert.HabitAlertBuilder habitAlertBuilder = HabitAlert.postHabitAlert();

        habitAlertBuilder = habitAlertBuilder
                .runHabit(runHabit)
                .alertOrder(alertStatus)
                .alertStatus(ALERT_ON);

        if (alert == null) {
            habitAlertBuilder = habitAlertBuilder.alertTime(changeDefaultTime(runTime, alertStatus));
        } else {
            habitAlertBuilder.alertTime(alert);
        }

        return habitAlertBuilder.build();
    }

    public LocalTime updateHabitAlert(LocalTime changeTime, List<HabitAlert> alerts, Integer alertOrder, Boolean alertStatus) {
        HabitAlert habitAlert = alerts
                .stream()
                .filter(alert -> Objects.equals(alert.getAlertOrder(), alertOrder))
                .findFirst()
                .orElseThrow(() -> new ValidationException(ALERT_NOT_FOUND));
        habitAlert.patchAlertTime(changeTime);
        habitAlert.updateAlertStatus(alertStatus);
        return habitAlert.getAlertTime();
    }

    /**
     * runHabitId와 alertOrder로 알림을 찾고 시간을 변경한다.
     */
    @Transactional
    public HabitAlert updateTimeByRunHabitIdAndOrder(Integer runHabitId, Integer alertOrder, LocalTime alertTime) {
        HabitAlert habitAlert = habitAlertQueryService.getByRunHabitIdAndOrderOrElseThrow(runHabitId, alertOrder);
        habitAlert.setAlertTime(alertTime);
        return habitAlert;
    }

    /**
     * 사용자가 알림 값 입력 안 했을 때 설정한 기본 시간으로 반환한다
     */
    private LocalTime changeDefaultTime(LocalTime runTime, int alertStatus) {
        if (alertStatus == FIRST_ALERT_STATUS) {
            return runTime.minusMinutes(FIRST_ALERT_DEFAULT);
        } else if (alertStatus == SECOND_ALERT_STATUS) {
            return runTime.plusMinutes(SECOND_ALERT_DEFAULT);
        }
        throw new ValidationException(ALERT_ORDER_NOT_FOUND);
    }
}
