package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.ALERT_NOT_FOUND;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.ALERT_ORDER_NOT_FOUND;

@Service
public class HabitAlertCommandService {

    public static final int FIRST_ALERT_STATUS = 1;
    public static final boolean ALERT_ON = true;
    public static final int FIRST_ALERT_DEFAULT = 10;
    public static final int SECOND_ALERT_STATUS = 2;
    public static final int SECOND_ALERT_DEFAULT = 30;

    public HabitAlert makeAlert(RunHabit runHabit, LocalTime runTime, LocalTime alert, int alertStatus) {
        if (alert != null) {
            return HabitAlert.postHabitAlert()
                    .runHabit(runHabit)
                    .alertOrder(alertStatus)
                    .alertTime(alert)
                    .alertStatus(ALERT_ON)
                    .build();
        } else {
            return HabitAlert.postHabitAlert()
                    .runHabit(runHabit)
                    .alertOrder(alertStatus)
                    .alertTime(changeDefaultTime(runTime, alertStatus))
                    .alertStatus(ALERT_ON)
                    .build();
        }
    }

    public LocalTime updateHabitAlert(LocalTime changeTime, List<HabitAlert> alerts, Integer alertOrder, Boolean alertStatus) {
        HabitAlert habitAlert = alerts
                .stream()
                .filter(alert -> Objects.equals(alert.getAlertOrder(), alertOrder))
                .findFirst()
                .orElseThrow(() -> new ValidationException(ALERT_NOT_FOUND));
        habitAlert.updateAlertTime(changeTime);
        habitAlert.updateAlertStatus(alertStatus);
        return habitAlert.getAlertTime();
    }

    private LocalTime changeDefaultTime(LocalTime runTime, int alertStatus) {
        if (alertStatus == FIRST_ALERT_STATUS) {
            return runTime.minusMinutes(FIRST_ALERT_DEFAULT);
        } else if (alertStatus == SECOND_ALERT_STATUS) {
            return runTime.plusMinutes(SECOND_ALERT_DEFAULT);
        }
        throw new ValidationException(ALERT_ORDER_NOT_FOUND);
    }
}
