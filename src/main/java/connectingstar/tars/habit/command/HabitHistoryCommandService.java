package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.HabitErrorCode;
import connectingstar.tars.common.exception.errorcode.UserErrorCode;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitHistoryRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.HistoryPostRequest;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
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

    /**
     * 습관기록 저장
     *
     * @param param 습관 기록을 저장하기 위한 유저 ID, 진행중인 습관 ID, 만족도, 실천한 장소, 실천량, 느낀점, 휴식여부
     */
    public void saveHistory(HistoryPostRequest param) {
        User user = findUserByUserId(UserUtils.getUserId());

        checkTodayCreateHistoryHabit(user, param.getRunHabitId());

        RunHabit runHabit = findRunHabitByRunHabitId(param);
        HabitHistory build = HabitHistory.builder()
                .user(user)
                .runHabit(runHabit)
                .achievement(param.getAchievement())
                .review(param.getReview())
                .runDate(LocalDateTime.now())
                .runPlace(param.getRunPlace())
                .runValue(param.getBehaviorValue())
                .isRest(param.getIsRest())
                .build();
        habitHistoryRepository.save(build);
    }


    private RunHabit findRunHabitByRunHabitId(HistoryPostRequest param) {
        return runHabitRepository.findById(param.getRunHabitId()).orElseThrow(()
                -> new ValidationException(HabitErrorCode.RUN_HABIT_NOT_FOUND));
    }

    private User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new ValidationException(UserErrorCode.USER_NOT_FOUND));
    }

    private void checkTodayCreateHistoryHabit(User user, Integer runHabitId) {
        Optional<HabitHistory> recentHistory = user.getHabitHistories()
                .stream()
                .filter(habitHistory -> Objects.equals(habitHistory.getRunHabit().getRunHabitId(), runHabitId))
                .sorted(Comparator.comparingInt(HabitHistory::getHabitHistoryId).reversed())
                .findFirst();
        if (recentHistory.isPresent()) {
            if (recentHistory.get().getCreatedAt().toLocalDate().isEqual(LocalDate.now()))
                throw new ValidationException(HabitErrorCode.ALREADY_CREATED_HABIT_HISTORY);
        }
    }
}