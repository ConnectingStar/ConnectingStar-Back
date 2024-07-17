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
import java.time.LocalTime;
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

    public static final int REST_VALUE = 0;
    private final HabitHistoryRepository habitHistoryRepository;
    private final RunHabitRepository runHabitRepository;
    private final UserRepository userRepository;

    /**
     * 습관기록 저장
     *
     * @param param 진행중인 습관 ID, 만족도, 실천한 장소, 실천량, 느낀점
     */
    public void saveHistory(HistoryPostRequest param) {
        User user = findUserByUserId(UserUtils.getUserId());

        checkExpiration(user, param);
        checkTodayCreateHistoryHabit(user, param);

        RunHabit runHabit = findRunHabitByRunHabitId(param);
        HabitHistory build = HabitHistory.builder()
                .user(user)
                .runHabit(runHabit)
                // TODO: add time
                .runDate(LocalDateTime.of(param.getReferenceDate().getYear(),param.getReferenceDate().getMonth().getValue(),param.getReferenceDate().getDayOfMonth(),
                    LocalTime.now().getHour(),LocalTime.now().getMinute()))
                .runPlace(param.getRunPlace())
                .action(param.getAction())
                .runValue(param.getBehaviorValue())
                .achievement(param.getAchievement())
                .review(param.getReview())
                .isRest(param.getAchievement() == REST_VALUE)
                .build();
        habitHistoryRepository.save(build);
    }

    /**
     * 습관 기록 기간이 유효한 지 반환
     */
    private void checkExpiration(User user, HistoryPostRequest param) {
        if(!LocalDate.now().minusDays(2).isBefore(param.getReferenceDate()))
            throw new ValidationException(HabitErrorCode.EXPIRED_DATE);
    }


    private RunHabit findRunHabitByRunHabitId(HistoryPostRequest param) {
        return runHabitRepository.findById(param.getRunHabitId()).orElseThrow(()
                -> new ValidationException(HabitErrorCode.RUN_HABIT_NOT_FOUND));
    }

    private User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new ValidationException(UserErrorCode.USER_NOT_FOUND));
    }

    private void checkTodayCreateHistoryHabit(User user, HistoryPostRequest param) {
        Optional<HabitHistory> recentHistory = user.getHabitHistories()
                .stream()
                .filter(habitHistory -> Objects.equals(habitHistory.getRunHabit().getRunHabitId(), param.getRunHabitId()))
                .filter(habitHistory -> param.getReferenceDate().isEqual(habitHistory.getRunDate().toLocalDate()))
                .sorted(Comparator.comparingInt(HabitHistory::getHabitHistoryId).reversed())
                .findFirst();
        if (recentHistory.isPresent()) {
            if (recentHistory.get().getRunDate().toLocalDate().isEqual(param.getReferenceDate()))
                throw new ValidationException(HabitErrorCode.ALREADY_CREATED_HABIT_HISTORY);
        }
    }
}