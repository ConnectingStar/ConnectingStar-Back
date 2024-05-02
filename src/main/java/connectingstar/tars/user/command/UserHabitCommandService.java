package connectingstar.tars.user.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.command.HabitAlertCommandService;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserDetail;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.UserOnboardingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static Integer findUserId() {
        UserDetail details = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (details != null) {
            return details.getUserId();
        } else throw new ValidationException(USER_NOT_FOUND);
    }

    /**
     * 온보딩 정보 등록
     *
     * @param param 등록 정보
     */
    @Transactional
    public void save(UserOnboardingRequest param) {
        User user = findUserByUserId();
        user.updateOnboarding(param);
        saveRunHabit(param, user);
    }

    public User findUserByUserId() {
        return userRepository.findById(findUserId()).orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
    }

    private void saveRunHabit(UserOnboardingRequest param, User user) {
        RunHabit runHabit = RunHabit.postRunHabit()
                .identity(param.getIdentity())
                .user(user)
                .runTime(param.getRunTime())
                .place(param.getPlace())
                .action(param.getAction())
                .value(param.getValue())
                .unit(param.getUnit())
                .build();
        HabitAlert firstHabitAlert =
                habitAlertCommandService.makeAlert(runHabit, param.getRunTime(), param.getFirstAlert(), FIRST_ALERT_STATUS);
        HabitAlert secondHabitAlert =
                habitAlertCommandService.makeAlert(runHabit, param.getRunTime(), param.getSecondAlert(), SECOND_ALERT_STATUS);
        runHabit.addAlert(habitAlertRepository.save(firstHabitAlert));
        runHabit.addAlert(habitAlertRepository.save(secondHabitAlert));
        runHabitRepository.save(runHabit);
    }
}
