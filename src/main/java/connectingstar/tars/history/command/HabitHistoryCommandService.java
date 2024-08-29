package connectingstar.tars.history.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.HabitErrorCode;
import connectingstar.tars.common.exception.errorcode.UserErrorCode;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.request.HabitHistoryPostRequest;
import connectingstar.tars.habit.request.HabitHistoryRestPostRequest;
import connectingstar.tars.habit.request.HistoryRestPostRequest;
import connectingstar.tars.habit.response.HabitHistoryPostResponse;
import connectingstar.tars.habit.response.HabitHistoryRestPostResponse;
import connectingstar.tars.habit.response.HistoryRestPostResponse;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.history.mapper.HabitHistoryMapper;
import connectingstar.tars.history.repository.HabitHistoryRepository;
import connectingstar.tars.history.request.HistoryPostRequest;
import connectingstar.tars.history.response.HistoryPostResponse;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    /**
     * 습관 기록 생성 기간.
     * 이 일자를 넘어가면 습관을 생성할 수 없습니다. (만료)
     */
    public static final int HISTORY_CREATION_PERIOD_DAYS = 1;
    /**
     * 실천 기록 생성 보상 별 개수
     */
    public static final int HISTORY_CREATION_REWARD_STAR_COUNT = 1;

    private final HabitHistoryRepository habitHistoryRepository;
    private final RunHabitRepository runHabitRepository;
    private final UserRepository userRepository;

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    private final HabitHistoryMapper habitHistoryMapper;

    /**
     * 습관 기록 저장.
     * 휴식이 아닌 기록을 저장합니다. 휴식 기록 저장은 `saveRestHistory`에서 처리.
     *
     * @param request 진행중인 습관 ID, 만족도, 실천한 장소, 실천량, 느낀점
     */
    @Transactional
    public HistoryPostResponse save(HistoryPostRequest request) {
        User user = userQueryService.getCurrentUserOrElseThrow();

        validateReferenceDateForCreation(request.getReferenceDate());
        validateAlreadyExistsOnReferenceDate(user, request.getRunHabitId(), request.getReferenceDate());

        RunHabit runHabit = findRunHabitByRunHabitId(request.getRunHabitId());

        HabitHistory habitHistory = HabitHistory.builder()
                .user(user)
                .runHabit(runHabit)
                .runDate(
                        LocalDateTime.of(
                                request.getReferenceDate().getYear(),
                                request.getReferenceDate().getMonth().getValue(),
                                request.getReferenceDate().getDayOfMonth(),
                                request.getRunTime().getHour(),
                                request.getRunTime().getMinute()
                        )
                )
                .runPlace(request.getRunPlace())
                .action(request.getAction())
                .runValue(request.getRunValue())
                .achievement(request.getAchievement())
                .review(request.getReview())
                .isRest(false)
                .build();

        HabitHistory savedHistory = habitHistoryRepository.save(habitHistory);

        // [FU-24] 실천 기록 보상 별 부여
        userCommandService.addStar(user, HISTORY_CREATION_REWARD_STAR_COUNT);

        return habitHistoryMapper.toPostResponse(savedHistory);
    }

    /**
     * 습관 기록 저장.
     * 휴식이 아닌 기록을 저장합니다. 휴식 기록 저장은 `saveRestHistory`에서 처리.
     *
     * @param param 진행중인 습관 ID, 만족도, 실천한 장소, 실천량, 느낀점
     * @deprecated
     */
    @Deprecated
    public HabitHistoryPostResponse saveHistory(HabitHistoryPostRequest param) {
        User user = findUserByUserId(UserUtils.getUserId());

        validateReferenceDateForCreation(param.getReferenceDate());
        validateAlreadyExistsOnReferenceDate(user, param.getRunHabitId(), param.getReferenceDate());

        RunHabit runHabit = findRunHabitByRunHabitId(param.getRunHabitId());

        HabitHistory habitHistory = HabitHistory.builder()
                .user(user)
                .runHabit(runHabit)
                .runDate(
                        LocalDateTime.of(
                                param.getReferenceDate().getYear(),
                                param.getReferenceDate().getMonth().getValue(),
                                param.getReferenceDate().getDayOfMonth(),
                                param.getRunTime().getHour(),
                                param.getRunTime().getMinute()
                        )
                )
                .runPlace(param.getRunPlace())
                .action(param.getAction())
                .runValue(param.getBehaviorValue())
                .achievement(param.getAchievement())
                .review(param.getReview())
                .isRest(false)
                .build();

        HabitHistory savedHistory = habitHistoryRepository.save(habitHistory);

        return habitHistoryMapper.toPostResponseV1(savedHistory);
    }

    /**
     * 휴식 기록 저장
     */
    public HistoryRestPostResponse saveRest(HistoryRestPostRequest request) {
        User user = findUserByUserId(UserUtils.getUserId());

        validateReferenceDateForCreation(request.getReferenceDate());
        validateAlreadyExistsOnReferenceDate(user, request.getRunHabitId(), request.getReferenceDate());

        RunHabit runHabit = findRunHabitByRunHabitId(request.getRunHabitId());

        HabitHistory habitHistory = HabitHistory.builder()
                .user(user)
                .runHabit(runHabit)
                .runDate(
                        LocalDateTime.of(
                                request.getReferenceDate().getYear(),
                                request.getReferenceDate().getMonth().getValue(),
                                request.getReferenceDate().getDayOfMonth(),
                                runHabit.getRunTime().getHour(),
                                runHabit.getRunTime().getMinute()
                        )
                )
                .review(request.getReview())
                .isRest(true)
                .build();

        HabitHistory savedHistory = habitHistoryRepository.save(habitHistory);

        return habitHistoryMapper.toRestPostResponse(savedHistory);
    }

    /**
     * 휴식 기록 저장
     *
     * @deprecated use {@link #saveRest(HistoryRestPostRequest)} instead
     */
    @Deprecated
    public HabitHistoryRestPostResponse saveRestHistory(HabitHistoryRestPostRequest param) {
        User user = findUserByUserId(UserUtils.getUserId());

        validateReferenceDateForCreation(param.getReferenceDate());
        validateAlreadyExistsOnReferenceDate(user, param.getRunHabitId(), param.getReferenceDate());

        RunHabit runHabit = findRunHabitByRunHabitId(param.getRunHabitId());

        HabitHistory habitHistory = HabitHistory.builder()
                .user(user)
                .runHabit(runHabit)
                .runDate(
                        LocalDateTime.of(
                                param.getReferenceDate().getYear(),
                                param.getReferenceDate().getMonth().getValue(),
                                param.getReferenceDate().getDayOfMonth(),
                                runHabit.getRunTime().getHour(),
                                runHabit.getRunTime().getMinute()
                        )
                )
                .review(param.getReview())
                .isRest(true)
                .build();

        HabitHistory savedHistory = habitHistoryRepository.save(habitHistory);

        return habitHistoryMapper.toRestPostResponseV1(savedHistory);
    }

    /**
     * [FU-23] 오늘 날짜를 기준으로 습관 기록 기준일이 유효한 지 검증합니다.
     * <p>
     * 습관 기록 기간이 유효하지 않으면 exception을 throw합니다.
     *
     * @param referenceDate 습관 기록 기준일
     */
    private void validateReferenceDateForCreation(LocalDate referenceDate) {
        // [FU-23] 마감일 = 기록 기준일의 하루 뒤 자정까지
        LocalDate dueDate = referenceDate.plusDays(HISTORY_CREATION_PERIOD_DAYS);

        if (LocalDate.now().isAfter(dueDate))
            throw new ValidationException(HabitErrorCode.EXPIRED_DATE);
    }


    private RunHabit findRunHabitByRunHabitId(Integer runHabitId) {
        return runHabitRepository.findById(runHabitId).orElseThrow(()
                -> new ValidationException(HabitErrorCode.RUN_HABIT_NOT_FOUND));
    }

    private User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new ValidationException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * 기록하려는 습관 기준일에 같은 날짜의 습관 기록이 존재하는 지 확인합니다.
     * 같은 날짜의 습관 기록이 존재하면 예외를 throw합니다.
     */
    private void validateAlreadyExistsOnReferenceDate(User user, Integer runHabitId, LocalDate referenceDate) {
        // TODO: 필요한 기록만 불러오도록 리팩토링
        Optional<HabitHistory> recentHistory = user.getHabitHistories()
                .stream()
                .filter(habitHistory -> Objects.equals(habitHistory.getRunHabit().getRunHabitId(), runHabitId))
                .filter(habitHistory -> referenceDate.isEqual(habitHistory.getRunDate().toLocalDate()))
                .sorted(Comparator.comparingInt(HabitHistory::getHabitHistoryId).reversed())
                .findFirst();
        if (recentHistory.isPresent()) {
            if (recentHistory.get().getRunDate().toLocalDate().isEqual(referenceDate))
                throw new ValidationException(HabitErrorCode.ALREADY_CREATED_HABIT_HISTORY);
        }
    }


}