package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.UserErrorCode;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import connectingstar.tars.habit.repository.HabitHistoryRepository;
import connectingstar.tars.habit.repository.QuitHabitRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.RunDeleteRequest;
import connectingstar.tars.habit.request.RunPostRequest;
import connectingstar.tars.habit.request.RunPutRequest;
import connectingstar.tars.habit.response.RunPutResponse;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public static final boolean NOT_REST = false;
    public static final boolean REST = true;

    private final RunHabitRepository runHabitRepository;
    private final HabitAlertRepository habitAlertRepository;
    private final QuitHabitRepository quitHabitRepository;
    private final HabitHistoryRepository habitHistoryRepository;
    private final UserRepository userRepository;

    /**
     * 진행중인 습관 생성
     *
     * @param param 진행중인 습관 생성을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     */
    @Transactional
    public void saveRun(RunPostRequest param) {
        User user = findUserByUserId(param.getUserId());
        RunHabit runHabit = RunHabit.postRunHabit()
                .identity(param.getIdentity())
                .user(user)
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
        runHabitRepository.save(runHabit);
        //추후 필요시 Return 값 추가 예정
    }

    /**
     * 진행중인 습관 수정
     *
     * @param param 진행중인 습관 수정을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     * @return 입력값을 그대로 반환합니다.(추후 필요한 값만 반환하도록 수정필요)
     */
    @Transactional
    public RunPutResponse updateRun(RunPutRequest param) {
        RunHabit runHabit = findRunHabitByRunHabitId(param.getRunHabitId());

        runHabit.updateData(param);
        List<HabitAlert> alerts = runHabit.getAlerts();
        LocalTime firstAlertTime = updateHabitAlert(param.getFirstAlert(), alerts, FIRST_ALERT_STATUS);
        LocalTime secondAlertTime = updateHabitAlert(param.getSecondAlert(), alerts, SECOND_ALERT_STATUS);
        return new RunPutResponse(runHabit, firstAlertTime, secondAlertTime);

    }

    /**
     * 진행중인 습관 삭제
     *
     * @param param 진행중인 습관 삭제를 위한 사용자 PK, 진행중인 습관 ID, 삭제 이유
     */
    public void deleteRun(RunDeleteRequest param) {
        User user = findUserByUserId(param.getUserId());
        RunHabit runHabit = findRunHabitByRunHabitId(param.getRunHabitId());
        List<HabitHistory> habitHistories = runHabit.getHabitHistories();


        QuitHabit quitHabit = QuitHabit.postQuitHabit()
            .runTime(runHabit.getRunTime())
            .user(user)
            .place(runHabit.getPlace())
            .action(runHabit.getAction())
            .value(findValue(habitHistories, NOT_REST))
            .restValue(findValue(habitHistories, REST))
            .reasonOfQuit(param.getReason())
            .startDate(runHabit.getCreatedAt())
            .quitDate(LocalDateTime.now())
            .build();
        quitHabitRepository.save(quitHabit);
        habitHistoryRepository.deleteAll(habitHistories);
        runHabitRepository.delete(runHabit);
    }

    private User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ValidationException(UserErrorCode.USER_NOT_FOUND));
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

    private RunHabit findRunHabitByRunHabitId(Integer runHabitId) {
        return runHabitRepository.findById(runHabitId).orElseThrow(() -> new ValidationException(RUN_HABIT_NOT_FOUND));
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

    private Integer findValue(List<HabitHistory> habitHistories, Boolean restValue) {
        return habitHistories.stream().filter(habitHistory -> habitHistory.getIsRest() == restValue).toList().size();
    }
}
