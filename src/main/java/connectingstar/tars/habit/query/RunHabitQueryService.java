package connectingstar.tars.habit.query;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.RunHabitAndHistoryDto;
import connectingstar.tars.habit.enums.DailyTrackingStatus;
import connectingstar.tars.habit.mapper.HabitHistoryMapper;
import connectingstar.tars.habit.mapper.RunHabitMapper;
import connectingstar.tars.habit.repository.HabitHistoryRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.repository.RunHabitRepositoryCustom;
import connectingstar.tars.habit.request.RunDayGetRequest;
import connectingstar.tars.habit.request.RunGetRequest;
import connectingstar.tars.habit.request.param.HabitDailyTrackingRequestParam;
import connectingstar.tars.habit.response.*;
import connectingstar.tars.user.command.UserHabitCommandService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.RUN_HABIT_NOT_FOUND;

/**
 * 진행중인 습관 조회 서비스
 *
 * @author 김성수
 */

@Service
@RequiredArgsConstructor
public class RunHabitQueryService {

    private final UserQueryService userQueryService;
    private final UserHabitCommandService userHabitCommandService;

    private final RunHabitRepository runHabitRepository;
    private final HabitHistoryRepository habitHistoryRepository;
    private final RunHabitRepositoryCustom runHabitRepositoryCustom;

    private final RunHabitMapper runHabitMapper;
    private final HabitHistoryMapper habitHistoryMapper;

    /**
     * 진행중인 습관 목록 조회
     *
     * @return 진행중인 습관 수정을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각 (추후 고치겠습니다 지금 시간이 없어서 ㅠ)
     * TODO: 리턴값 수정
     */
    public List<RunGetListResponse> getList() {

        List<RunHabit> allByUser = runHabitRepository.findAllByUser(userHabitCommandService.findUserByUserId());
        return allByUser.stream().map(RunGetListResponse::new).collect(Collectors.toList());
    }

    /**
     * 진행중인 습관 조회
     *
     * @return 진행중인 습관 수정을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각 (추후 고치겠습니다 지금 시간이 없어서 ㅠ)
     * TODO: 리턴값 수정
     */
    public RunPutResponse get(RunGetRequest param) {

        User userByUserId = userHabitCommandService.findUserByUserId();
        return userByUserId.getRunHabits()
                .stream()
                .filter(rh -> Objects.equals(rh.getRunHabitId(), param.getRunHabitId()))
                .map(RunPutResponse::new)
                .findFirst().orElseThrow(() -> new ValidationException(RUN_HABIT_NOT_FOUND));
    }

    /**
     * 메인 화면 - 날짜별 습관 여부 표시 용도
     */
    public List<HabitDayGetResponse> getDay(RunDayGetRequest param) {
        //이전 일자에 대해선 오늘기준으로 월~ 일의 데이터를 전달 -> 모든 습관이 존재할경우 true, false
        //오늘의 습관 다 꺼내옴 -> 만약 해빗 히스토리가 없는 경우 -> 기록가능 ->0
        User userByUserId = userHabitCommandService.findUserByUserId();
        return userByUserId.getRunHabits()
                .stream()
                .map(rh -> getDayResponse(param.getReferenceDate(), rh)).toList();
    }

    /**
     * @Deprecated use .getDailyTrackingList() instead.
     */
    @Deprecated
    private static @NotNull HabitDayGetResponse getDayResponse(LocalDate referenceDate, RunHabit runHabit) {
        Optional<HabitHistory> first = runHabit.getHabitHistories().stream()
                .filter(hh -> hh.getRunDate().toLocalDate().isEqual(referenceDate)).findFirst();
        Integer habitStatus = 0;
        if (first.isEmpty()) {
            //해빗 히스토리가 없는 경우 -> 오늘의 날짜와 조회한 date가 2일 이상 차이나는 경우 -> 기록 불가 -> 1
            if (referenceDate.isBefore(LocalDate.now().minusDays(2))) {
                habitStatus = 1;
                return new HabitDayGetResponse(runHabit, habitStatus);
            }
            return new HabitDayGetResponse(runHabit, habitStatus);
        }
        HabitHistory habitHistory = first.get();
        if (habitHistory.getIsRest() == false) {
            habitStatus = 2;
            return new HabitDayGetResponse(runHabit, habitStatus);
        }
        // 해빗 히스토리가 있는경우 -> status가 0일경우  -> 오늘 휴식 -> 3
        return new HabitDayGetResponse(runHabit, habitStatus);
    }

    public List<HabitHistoryWeekelyWriteResponse> getHistoryTotalWrite(RunDayGetRequest param) {
        User userByUserId = userHabitCommandService.findUserByUserId();
        int size = userByUserId.getRunHabits().size();
        LocalDate referenceDate = param.getReferenceDate();
        LocalDate thisWeekSunday = referenceDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate startDay = thisWeekSunday.atStartOfDay().toLocalDate();
        List<HabitHistoryWeekelyWriteResponse> responses = new ArrayList<>();
        int count = 1;
        while (count <= 7) {
            HabitHistoryWeekelyWriteResponse habitHistoryWeekelyWriteResponse = new HabitHistoryWeekelyWriteResponse(startDay,
                    checkTodayAllHabitHistoryCreate(startDay, userByUserId, size));
            responses.add(habitHistoryWeekelyWriteResponse);
            startDay = startDay.plusDays(1);
            count++;
        }
        return responses;
    }

    /**
     * 날짜를 입력받아 해당 날짜의 습관, 기록, 상태를 조회한다.
     * 홈 페이지 - 캘린더 - 날짜별 습관 수행을 조회할 때 사용한다.
     * <p>
     * 기록이 없어도 history=null, habit, status를 반환한다.
     */
    public List<HabitDailyTrackingGetResponse> getDailyTrackingList(HabitDailyTrackingRequestParam requestParam) {
        User user = userQueryService.getCurrentUser();

        List<RunHabitAndHistoryDto> runHabitWithHistories = runHabitRepositoryCustom.getListOfUserWithHistoryByDate(user.getId(), requestParam.getDate());

        List<HabitDailyTrackingGetResponse> responses = runHabitWithHistories.stream()
                .map(runHabitWithHistory -> {
                    HabitHistory history = runHabitWithHistory.getHabitHistory();
                    RunHabit runHabit = runHabitWithHistory.getRunHabit();

                    return HabitDailyTrackingGetResponse.builder()
                            .history(habitHistoryMapper.toDto(history))
                            .habit(runHabitMapper.toDto(runHabit))
                            .status(this.getDailyTrackingStatus(history, requestParam.getDate()))
                            .build();
                })
                .toList();

        return responses;
    }

    /**
     * 특정 날짜의 습관 수행 상태를 반환합니다.
     *
     * @param history (nullable) 해당 날짜의 습관 기록.
     * @param date    습관 기록을 확인하려는 날짜.
     */
    private DailyTrackingStatus getDailyTrackingStatus(HabitHistory history, LocalDate date) {
        // 기록 없음
        if (history == null) {
            // 만료 여부 - 이틀 이상 지나면 만료.
            if (date.isBefore(LocalDate.now().minusDays(2))) {
                return DailyTrackingStatus.EXPIRED;
            }
            return DailyTrackingStatus.TO_DO;
        }

        // 기록 있음
        // 휴식 여부
        if (history.getIsRest() == true) {
            return DailyTrackingStatus.REST;
        }
        return DailyTrackingStatus.COMPLETED;
    }

    private boolean checkTodayAllHabitHistoryCreate(LocalDate referenceDate, User user, Integer runHabitSize) {
        List<RunHabit> list = user.getRunHabits().stream()
                .filter(rh -> rh.getHabitHistories().stream().anyMatch(hh -> hh.getRunDate().toLocalDate().isEqual(referenceDate))).toList();
        return list.size() == runHabitSize;
    }
}
