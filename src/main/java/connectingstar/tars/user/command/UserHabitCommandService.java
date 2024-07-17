package connectingstar.tars.user.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.habit.command.HabitAlertCommandService;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.UserOnboardingRequest;
import connectingstar.tars.user.response.UserIdentityInfoResponse;
import connectingstar.tars.user.response.UserOnboardingResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;
import static connectingstar.tars.habit.command.RunHabitCommandService.FIRST_ALERT_STATUS;
import static connectingstar.tars.habit.command.RunHabitCommandService.SECOND_ALERT_STATUS;

@Service
@RequiredArgsConstructor
public class UserHabitCommandService {

    private final HabitAlertCommandService habitAlertCommandService;
    private final UserRepository userRepository;
    private final HabitAlertRepository habitAlertRepository;
    private final RunHabitRepository runHabitRepository;


    /**
     * 온보딩 정보 등록
     *
     * @param param 등록 정보
     */
    @Transactional
    public UserOnboardingResponse save(UserOnboardingRequest param) {
        User user = findUserByUserId();
        user.updateOnboarding(param);
        RunHabit runHabit = saveRunHabit(param, user);
        return new UserOnboardingResponse(user, runHabit);
    }

    public User findUserByUserId() {
        return userRepository.findById(UserUtils.getUserId()).orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
    }

  public List<UserIdentityInfoResponse> getUserIdentityInfo() {
    return findUserByUserId().getRunHabits().stream().map(habit -> new UserIdentityInfoResponse(habit.getIdentity())).toList();
  }

    /**
     * 온보딩 할 때 사용
     */
  private RunHabit saveRunHabit(UserOnboardingRequest param, User user) {
        RunHabit runHabit = RunHabit.postRunHabit()
                .identity(param.getIdentity())
                .user(user)
                .runTime(param.getRunTime().toLocalTime())
                .place(param.getPlace())
                .action(param.getBehavior())
                .value(param.getBehaviorValue())
                .unit(param.getBehaviorUnit())
                .build();
        user.updateIdentity(param.getIdentity());
        HabitAlert firstHabitAlert =
                habitAlertCommandService.makeAlert(runHabit, param.getRunTime().toLocalTime(), param.getFirstAlert().toLocalTime(), FIRST_ALERT_STATUS);
        HabitAlert secondHabitAlert =
                habitAlertCommandService.makeAlert(runHabit, param.getRunTime().toLocalTime(), param.getSecondAlert().toLocalTime(), SECOND_ALERT_STATUS);
        runHabit.addAlert(habitAlertRepository.save(firstHabitAlert));
        runHabit.addAlert(habitAlertRepository.save(secondHabitAlert));
        return runHabitRepository.save(runHabit);
    }
}
