package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.RunHabitPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.ALERT_ORDER_NOT_FOUND;

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
        runHabit.updateAlert(habitAlertRepository.save(firstHabitAlert));
        runHabit.updateAlert(habitAlertRepository.save(secondHabitAlert));
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
                    .alertTime(changeTime(runTime,alertStatus))
                    .alertStatus(ALERT_ON)
                    .build();
        }
    }

    private LocalTime changeTime(LocalTime runTime, int alertStatus) {
        if(alertStatus == FIRST_ALERT_STATUS){
            return runTime.minusMinutes(FIRST_ALERT_DEFAULT);
        } else if(alertStatus == SECOND_ALERT_STATUS){
            return runTime.plusMinutes(SECOND_ALERT_DEFAULT);
        }
        throw new ValidationException(ALERT_ORDER_NOT_FOUND);
    }

}
