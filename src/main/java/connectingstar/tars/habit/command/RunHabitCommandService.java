package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.mapper.RunHabitMapper;
import connectingstar.tars.habit.query.RunHabitQueryService;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import connectingstar.tars.habit.repository.QuitHabitRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.repository.RunHabitRepositoryCustom;
import connectingstar.tars.habit.request.*;
import connectingstar.tars.habit.response.*;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.history.repository.HabitHistoryRepository;
import connectingstar.tars.onboard.command.UserOnboardCommandService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.EXCEED_USER_MAX_COUNT;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.RUN_HABIT_NOT_FOUND;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

/**
 * 진행중인 습관의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 김성수
 */

@RequiredArgsConstructor
@Service
public class RunHabitCommandService {

    /**
     * 사용자가 가질 수 있는 최대 진행중인 습관 수
     */
    public static final Integer USER_MAX_COUNT = 3;
    public static final int FIRST_ALERT_STATUS = 1;
    public static final int SECOND_ALERT_STATUS = 2;
    public static final boolean NOT_REST = false;
    public static final boolean REST = true;
    public static final String IDENTITY_NOTHING = "없음";

    private final RunHabitRepository runHabitRepository;
    private final RunHabitRepositoryCustom runHabitRepositoryCustom;
    private final HabitAlertRepository habitAlertRepository;
    private final QuitHabitRepository quitHabitRepository;
    private final HabitHistoryRepository habitHistoryRepository;
    private final UserRepository userRepository;

    private final RunHabitQueryService runHabitQueryService;
    private final UserQueryService userQueryService;

    private final HabitAlertCommandService habitAlertCommandService;
    private final UserOnboardCommandService userOnboardCommandService;

    private final RunHabitMapper runHabitMapper;

    /**
     * 진행중인 습관 생성.
     */
    @Transactional
    public HabitPostResponse save(HabitPostRequest param) {
        User user = userQueryService.getCurrentUserOrElseThrow();

        validateUserMaxCount(user.getId());

        RunHabit runHabit = RunHabit.postBuilder()
                .identity(param.getIdentity())
                .user(user)
                .runTime(param.getRunTime())
                .place(param.getPlace())
                .action(param.getAction())
                .value(param.getValue())
                .unit(param.getUnit())
                .build();

        HabitAlert firstHabitAlert = habitAlertCommandService.makeAlert(runHabit, param.getRunTime(), param.getFirstAlert(), FIRST_ALERT_STATUS);
        HabitAlert secondHabitAlert = habitAlertCommandService.makeAlert(runHabit, param.getRunTime(), param.getSecondAlert(), SECOND_ALERT_STATUS);
        runHabit.addAlert(habitAlertRepository.save(firstHabitAlert));
        runHabit.addAlert(habitAlertRepository.save(secondHabitAlert));

        runHabitRepository.save(runHabit);

        if (param.getIsOnboarding() != null && param.getIsOnboarding()) {
            userOnboardCommandService.updateIsHabitCreated(user.getId(), true);
        }

        return runHabitMapper.toPostResponse(runHabit);
    }

    /**
     * 유저별 습관 개수가 최대 개수를 초과하면 예외를 발생시킨다.
     */
    private void validateUserMaxCount(Integer userId) {
        User userReference = userRepository.getReferenceById(userId);
        Integer userRunHabitCount = runHabitRepository.countByUser(userReference);

        if (userRunHabitCount >= USER_MAX_COUNT) {
            throw new ValidationException(EXCEED_USER_MAX_COUNT);
        }
    }

    /**
     * 진행중인 습관 생성
     *
     * @param param 진행중인 습관 생성을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     * @deprecated use {@link connectingstar.tars.habit.command.RunHabitCommandService#save} instead
     */
    @Transactional
    @Deprecated
    public RunPostResponse save(RunPostRequest param) {
        User user = findUserByUserId(UserUtils.getUserId());
        RunHabit runHabit = RunHabit.postBuilder()
                .identity(param.getIdentity())
                .user(user)
                .runTime(param.getRunTime().toLocalTime())
                .place(param.getPlace())
                .action(param.getBehavior())
                .value(param.getBehaviorValue())
                .unit(param.getBehaviorUnit())
                .build();
        HabitAlert firstHabitAlert = habitAlertCommandService.makeAlert(runHabit, param.getRunTime().toLocalTime(), param.getFirstAlert().toLocalTime(), FIRST_ALERT_STATUS);
        HabitAlert secondHabitAlert = habitAlertCommandService.makeAlert(runHabit, param.getRunTime().toLocalTime(), param.getSecondAlert().toLocalTime(), SECOND_ALERT_STATUS);
        runHabit.addAlert(habitAlertRepository.save(firstHabitAlert));
        runHabit.addAlert(habitAlertRepository.save(secondHabitAlert));
        runHabitRepository.save(runHabit);
        return new RunPostResponse(runHabit.getRunHabitId());
    }

    /**
     * 진행중인 습관 수정
     * 습관 알람 수정 포함
     *
     * @param param 진행중인 습관 수정을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     * @return 입력값을 그대로 반환합니다.(추후 필요한 값만 반환하도록 수정필요)
     */
    @Deprecated
    @Transactional
    public RunPutResponse updateRun(RunPutRequest param) {
        RunHabit runHabit = findRunHabitByRunHabitId(param.getRunHabitId());
        User user = userRepository.findById(UserUtils.getUserId()).orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
        runHabit.updateData(param, user);
        List<HabitAlert> alerts = runHabit.getAlerts();
        habitAlertCommandService.updateHabitAlert(param.getFirstAlert().toLocalTime(), alerts, FIRST_ALERT_STATUS, param.getFirstAlertStatus());
        habitAlertCommandService.updateHabitAlert(param.getSecondAlert().toLocalTime(), alerts, SECOND_ALERT_STATUS, param.getSecondAlertStatus());
        return new RunPutResponse(runHabit);

    }

    /**
     * 진행중인 내 습관을 일부 수정합니다.
     * null인 필드는 수정하지 않습니다.
     */
    @Transactional
    public HabitPatchResponse patchMineById(Integer runHabitId, HabitPatchRequest param) {
        RunHabit runHabit = runHabitQueryService.getMineByIdOrElseThrow(runHabitId);

        if (param.getIdentity() != null) {
            runHabit.setIdentity(param.getIdentity());
        }
        if (param.getRunTime() != null) {
            runHabit.setRunTime(param.getRunTime());
        }
        if (param.getPlace() != null) {
            runHabit.setPlace(param.getPlace());
        }
        if (param.getAction() != null) {
            runHabit.setAction(param.getAction());
        }
        if (param.getValue() != null) {
            runHabit.setValue(param.getValue());
        }
        if (param.getUnit() != null) {
            runHabit.setUnit(param.getUnit());
        }

        if (param.getFirstAlert() != null) {
            habitAlertCommandService.updateTimeByRunHabitIdAndOrder(runHabit.getRunHabitId(), 1, param.getFirstAlert());
        }
        if (param.getSecondAlert() != null) {
            habitAlertCommandService.updateTimeByRunHabitIdAndOrder(runHabit.getRunHabitId(), 2, param.getSecondAlert());
        }

        return runHabitMapper.toPatchResponse(runHabit, runHabit.getAlerts());
    }

    /**
     * 진행중인 습관 삭제
     * RunHabit 레코드를 QuitHabit으로 복사하고, RunHabit 레코드를 hard delete한다
     *
     * @param param 진행중인 습관 삭제를 위한 사용자 PK, 진행중인 습관 ID, 삭제 이유
     */
    public void deleteRun(RunDeleteRequest param) {
        User user = findUserByUserId(UserUtils.getUserId());
        RunHabit runHabit = runHabitRepositoryCustom.checkUserId(param.getRunHabitId());
        List<HabitHistory> habitHistories = runHabit.getHabitHistories();
        if (runHabit.getIdentity().equals(user.getIdentity())) {
            Optional<RunHabit> first = user.getRunHabits().stream().filter(rh -> !rh.getIdentity().equals(user.getIdentity())).findFirst();
            if (first.isEmpty()) user.updateIdentity(IDENTITY_NOTHING);
            else user.updateIdentity(first.get().getIdentity());
        }
        QuitHabit quitHabit = QuitHabit.postQuitHabit()
                .runTime(runHabit.getRunTime())
                .user(user)
                .place(runHabit.getPlace())
                .action(runHabit.getAction())
                .value(findValue(habitHistories, NOT_REST))
                .unit(runHabit.getUnit())
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
                new ValidationException(USER_NOT_FOUND));
    }

    private RunHabit findRunHabitByRunHabitId(Integer runHabitId) {
        return runHabitRepository.findById(runHabitId).orElseThrow(() -> new ValidationException(RUN_HABIT_NOT_FOUND));
    }

    /**
     * 휴식이 몇 번인 지 반환한다
     */
    private Integer findValue(List<HabitHistory> habitHistories, Boolean restValue) {
        return habitHistories.stream().filter(habitHistory -> habitHistory.getIsRest() == restValue).toList().size();
    }

    /**
     * 진행중인 습관 삭제.
     * RunHabit 레코드를 QuitHabit으로 복사하고, RunHabit 레코드를 hard delete한다.
     * runHabitId에 해당하는 습관이 현재 로그인한 유저의 습관이 아니면 예외를 발생시킨다.
     */
    public HabitDeleteResponse deleteMineById(Integer runHabitId) {
        User user = userQueryService.getCurrentUserOrElseThrow();
        RunHabit runHabit = runHabitQueryService.getMineByIdOrElseThrow(runHabitId, user);

        List<HabitHistory> habitHistories = runHabit.getHabitHistories();
        if (runHabit.getIdentity().equals(user.getIdentity())) {
            Optional<RunHabit> first = user.getRunHabits().stream().filter(rh -> !rh.getIdentity().equals(user.getIdentity())).findFirst();
            if (first.isEmpty()) user.updateIdentity(IDENTITY_NOTHING);
            else user.updateIdentity(first.get().getIdentity());
        }
        QuitHabit quitHabit = QuitHabit.builder()
                .runTime(runHabit.getRunTime())
                .user(user)
                .place(runHabit.getPlace())
                .action(runHabit.getAction())
                // TODO: repository.count
                .completedHistoryCount(findValue(habitHistories, NOT_REST))
                .unit(runHabit.getUnit())
                // TODO: count
                .restHistoryCount(findValue(habitHistories, REST))
                .reasonOfQuit(param.getReason())
                .startDate(runHabit.getCreatedAt())
                .quitDate(LocalDateTime.now())
                .build();
        quitHabitRepository.save(quitHabit);
        habitHistoryRepository.deleteAll(habitHistories);
        runHabitRepository.delete(runHabit);


        runHabitRepository.delete(runHabit);

        return new HabitDeleteResponse(runHabitId);
    }
}
