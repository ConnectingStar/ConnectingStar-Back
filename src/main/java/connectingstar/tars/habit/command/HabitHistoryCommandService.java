package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.HabitErrorCode;
import connectingstar.tars.common.exception.errorcode.UserErrorCode;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitHistoryRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.HabitHistoryPostRequest;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

/**
 * 습관기록의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 김성수
 */

@RequiredArgsConstructor
@Service
public class HabitHistoryCommandService {

    private final HabitHistoryRepository habitHistoryRepository;
    private final RunHabitRepository runHabitRepository;
    private final UserRepository userRepository;

    public void postHistoryHabit(HabitHistoryPostRequest param) {
        User user = userRepository.findById(param.getUserId()).orElseThrow(()
                -> new ValidationException(UserErrorCode.USER_NOT_FOUND));

        checkTodayCreateHistoryHabit(user);

        RunHabit runHabit = runHabitRepository.findById(param.getRunHabitId()).orElseThrow(()
                -> new ValidationException(HabitErrorCode.RUN_HABIT_NOT_FOUND));
        HabitHistory build = HabitHistory.builder()
                .user(user)
                .runHabit(runHabit)
                .achievement(param.getAchievement())
                .review(param.getReview())
                .runDate(LocalDateTime.now())
                .runPlace(param.getRunPlace())
                .runValue(param.getRunValue())
                .isRest(param.getIsRest())
                .build();
        habitHistoryRepository.save(build);
    }

    private void checkTodayCreateHistoryHabit(User user) {
        Optional<HabitHistory> recentHistory = user.getHabitHistories()
                .stream()
                .sorted(Comparator.comparingInt(HabitHistory::getHabitHistoryId).reversed())
                .findFirst();
        if(recentHistory.isPresent()){
            if(recentHistory.get().getCreatedAt().toLocalDate().isEqual(LocalDate.now()))
                throw new ValidationException(HabitErrorCode.ALREADY_CREATED_HABIT_HISTORY);
        }
    }
}