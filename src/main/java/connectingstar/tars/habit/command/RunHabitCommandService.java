package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.RunHabitPostRequest;
import connectingstar.tars.habit.request.RunHabitPutRequest;
import connectingstar.tars.habit.response.RunHabitPutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.*;

/**
 * 진행중인 습관의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 김성수
 */

@RequiredArgsConstructor
@Service
public class RunHabitCommandService {

    public static final int FIRST_ALERT_STATUS = 1;
    public static final boolean ALERT_ON = true;
    public static final int FIRST_ALERT_DEFAULT = 10;
    public static final int SECOND_ALERT_STATUS = 2;
    public static final int SECOND_ALERT_DEFAULT = 30;

    private final RunHabitRepository runHabitRepository;
    private final HabitAlertRepository habitAlertRepository;

    /**
     * 진행중인 습관 생성
     *
     * @param param {@link RunHabitPostRequest}
     */
    @Transactional
    public RunHabit postRunHabit(RunHabitPostRequest param) {
        RunHabit runHabit = RunHabit.postRunHabit()
                .identity(param.getIdentity())
                .runTime(param.getRunTime())
                .place(param.getPlace())
                .action(param.getAction())
                .value(param.getValue())
                .unit(param.getUnit())
                .build();
        HabitAlert firstHabitAlert = makeAlert(runHabit, param.getRunTime(), param.getFirstAlert(), FIRST_ALERT_STATUS);
        HabitAlert secondHabitAlert = makeAlert(runHabit, param.getRunTime(), param.getSecondAlert(), SECOND_ALERT_STATUS);
        runHabit.addAlert(habitAlertRepository.save(firstHabitAlert));
        runHabit.addAlert(habitAlertRepository.save(secondHabitAlert));
        return runHabitRepository.save(runHabit);

    }

    private HabitAlert makeAlert(RunHabit runHabit,LocalTime runTime, LocalTime alert, int alertStatus) {
        if(alert != null){
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
                    .alertTime(changeDefaultTime(runTime,alertStatus))
                    .alertStatus(ALERT_ON)
                    .build();
        }
    }

    private LocalTime changeDefaultTime(LocalTime runTime, int alertStatus) {
        if(alertStatus == FIRST_ALERT_STATUS){
            return runTime.minusMinutes(FIRST_ALERT_DEFAULT);
        } else if(alertStatus == SECOND_ALERT_STATUS){
            return runTime.plusMinutes(SECOND_ALERT_DEFAULT);
        }
        throw new ValidationException(ALERT_ORDER_NOT_FOUND);
    }

    /**
     * 진행중인 습관 수정
     *
     * @param param {@link RunHabitPutRequest}
     */
    @Transactional
    public RunHabitPutResponse putRunHabit(RunHabitPutRequest param) {
        RunHabit runHabit = runHabitRepository.findById(param.getRunHabitId()).orElseThrow(() -> new ValidationException(RUN_HABIT_NOT_FOUND));

        runHabit.updateData(param);
        List<HabitAlert> alerts = runHabit.getAlerts();
        LocalTime firstAlertTime = updateHabitAlert(param.getFirstAlert(), alerts, FIRST_ALERT_STATUS);
        LocalTime secondAlertTime = updateHabitAlert(param.getSecondAlert(), alerts, SECOND_ALERT_STATUS);
        return new RunHabitPutResponse(runHabit, firstAlertTime, secondAlertTime);

    }

    private LocalTime updateHabitAlert(LocalTime changeTime, List<HabitAlert> alerts , Integer alertOrder) {
        HabitAlert habitAlert = alerts
                .stream()
                .filter(alert -> Objects.equals(alert.getAlertOrder(), alertOrder))
                .findFirst()
                .orElseThrow(() -> new ValidationException(ALERT_NOT_FOUND));
        habitAlert.updateAlertTime(changeTime);
        return habitAlert.getAlertTime();
    }
}
