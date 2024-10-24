package connectingstar.tars.habit.query;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.device.domain.Device;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.RunHabitAndHistoryDto;
import connectingstar.tars.habit.dto.RunHabitDto;
import connectingstar.tars.habit.dto.RunHabitWithDevice;
import connectingstar.tars.habit.enums.DailyTrackingStatus;
import connectingstar.tars.habit.mapper.RunHabitMapper;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.habit.repository.RunHabitRepositoryCustom;
import connectingstar.tars.habit.request.RunDayGetRequest;
import connectingstar.tars.habit.request.RunGetRequest;
import connectingstar.tars.habit.request.param.HabitDailyTrackingRequestParam;
import connectingstar.tars.habit.request.param.HabitGetListRequestParam;
import connectingstar.tars.habit.request.param.HabitGetOneRequestParam;
import connectingstar.tars.habit.response.*;
import connectingstar.tars.history.command.HabitHistoryCommandService;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.history.mapper.HabitHistoryMapper;
import connectingstar.tars.history.repository.HabitHistoryRepository;
import connectingstar.tars.pushnotification.dto.PushNotificationMessage;
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

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.NOT_USER_RUN_HABIT;
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
    private final HabitHistoryCommandService habitHistoryCommandService;

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
     * @deprecated use .getMyList() instead.
     */
    @Deprecated
    public List<RunGetListResponse> getList() {

        List<RunHabit> allByUser = runHabitRepository.findAllByUser(userHabitCommandService.findUserByUserId());
        return allByUser.stream().map(RunGetListResponse::new).collect(Collectors.toList());
    }

    /**
     * 진행중인 습관 조회
     *
     * @return 진행중인 습관 수정을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각 (추후 고치겠습니다 지금 시간이 없어서 ㅠ)
     * TODO: 리턴값 수정
     * @deprecated use .getMineById() instead.
     */
    @Deprecated
    public RunPutResponse get(RunGetRequest param) {

        User userByUserId = userHabitCommandService.findUserByUserId();
        return userByUserId.getRunHabits()
                .stream()
                .filter(rh -> Objects.equals(rh.getRunHabitId(), param.getRunHabitId()))
                .map(RunPutResponse::new)
                .findFirst().orElseThrow(() -> new ValidationException(RUN_HABIT_NOT_FOUND));
    }

    /**
     * 습관 id를 이용해서 습관을 조회합니다.
     * 습관이 없으면 RUN_HABIT_NOT_FOUND 예외를 발생합니다.
     */
    public RunHabit getByIdOrElseThrow(Integer runHabitId) {
        return runHabitRepository.findByRunHabitId(runHabitId)
                .orElseThrow(() -> new ValidationException(RUN_HABIT_NOT_FOUND));
    }

    /**
     * 습관 id를 이용해서 습관을 조회합니다.
     * id에 해당하는 습관이 없거나 현재 로그인한 유저의 습관이 아니면 예외를 발생합니다.
     */
    public RunHabit getMineByIdOrElseThrow(Integer runHabitId) {
        User currentUser = userQueryService.getCurrentUserOrElseThrow();

        return getMineByIdOrElseThrow(runHabitId, currentUser);
    }

    /**
     * 습관 id를 이용해서 습관을 조회합니다.
     * id에 해당하는 습관이 없거나 현재 로그인한 유저의 습관이 아니면 예외를 발생합니다.
     */
    public RunHabit getMineByIdOrElseThrow(Integer runHabitId, User currentUser) {
        RunHabit runHabit = getByIdOrElseThrow(runHabitId);

        if (runHabit.getUser().getId() != currentUser.getId()) {
            throw new ValidationException(NOT_USER_RUN_HABIT);
        }

        return runHabit;
    }

    /**
     * 습관 id를 이용해서 습관을 조회합니다.
     * id에 해당하는 습관이 없거나 현재 로그인한 유저의 습관이 아니면 예외를 발생합니다.
     *
     * @return 습관 조회 응답 DTO
     */
    public HabitGetOneResponse getMineById(Integer runHabitId, HabitGetOneRequestParam requestParam) {
        RunHabit runHabit = getMineByIdOrElseThrow(runHabitId);

        List<HabitAlert> habitAlerts = null;

        if (requestParam.getRelated() != null) {
            for (String related : requestParam.getRelated()) {
                if (related.equals("habitAlerts")) {
                    habitAlerts = runHabit.getAlerts();
                }
            }
        }

        return runHabitMapper.toGetOneResponse(runHabit, habitAlerts);
    }

    /**
     * 현재 로그인한 유저가 진행중인 습관 리스트를 반환합니다
     */
    public HabitGetListResponse getMyList(HabitGetListRequestParam requestParam) {
        User user = userQueryService.getCurrentUserOrElseThrow();
        List<RunHabit> runHabits = runHabitRepository.findAllByUser(user);

        if (requestParam.getSortBy() != null) {
            runHabits.sort((habit1, habit2) -> {
                switch (requestParam.getSortBy()) {
                    case CREATED_AT:
                        switch (requestParam.getSortOrder()) {
                            case ASC:
                            default:
                                return habit1.getCreatedAt().compareTo(habit2.getCreatedAt());
                            case DESC:
                                return habit2.getCreatedAt().compareTo(habit1.getCreatedAt());
                        }
                    default:
                        return 0;
                }
            });
        }

        List<RunHabitDto> dtos = runHabitMapper.toDtoList(runHabits);

        if (requestParam.getExpand() != null) {
            if (requestParam.getExpand().contains(HabitGetListRequestParam.Expand.HISTORY_COUNT_BY_STATUS)) {
                dtos.forEach(dto -> {
                    Integer completedHistoryCount = habitHistoryRepository.countByRunHabit_RunHabitIdAndIsRest(dto.getRunHabitId(), false);
                    Integer restHistoryCount = habitHistoryRepository.countByRunHabit_RunHabitIdAndIsRest(dto.getRunHabitId(), true);

                    dto.setHistoryCountByStatus(
                            RunHabitDto.HistoryCountByStatus.builder()
                                    .completedCount(completedHistoryCount)
                                    .restCount(restHistoryCount)
                                    .build()
                    );
                });
            }
        }

        return HabitGetListResponse.builder()
                .runHabits(dtos)
                .build();
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
        User user = userQueryService.getCurrentUserOrElseThrow();

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

    /**
     * 유저의 수행 시각이 1일 전인 기록이 존재하지 않는 습관을 fetch 합니다.
     * runDate가 어제인 history가 없는 습관 fetch.
     *
     * @author 이우진
     */
    public List<RunHabitWithDevice> getListByYesterdayHistoryNotExistWithDevice() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        return runHabitRepository.findAllByHistoryOfRunDateNotExistWithDevice(yesterday);
    }

    /**
     * 기록 독려 알림 메세지를 생성합니다
     *
     * @link <a href="https://www.figma.com/design/deVOGLOqzbCjKJP9fDeB3i/%ED%95%B4%EB%B9%97%EB%B2%84%EB%94%94?node-id=4076-4527&m=dev">Figma</a>
     */
    public PushNotificationMessage generateMissingHistoryPushNotificationMessage(RunHabit runHabit, Device device) {
        return PushNotificationMessage.builder()
                .token(device.getFcmRegistrationToken())
                .title(runHabit.getAction() + " 기록 리마인더\n")
                .body("앗,, 어제 " + runHabit.getAction() + " 기록이 없네요\uD83D\uDE25\n" +
                        "마감(자정) 전에 남기고 \"" + runHabit.getIdentity() + "\" 강화하기!")
                .build();
    }


    /**
     * 습관 id를 이용해서 통계 정보를 반환합니다.
     * 누적 별, 누적 실천량.
     * 통계 페이지에서 사용
     * <p>
     * TODO: 최적화 - 통계값 별도 저장 후 조회. 변경 될 때마다 통계값 UPDATE.
     */
    public HabitGetOneStatisticsResponse getMyStatisticsById(Integer runHabitId) {
        RunHabit runHabit = getMineByIdOrElseThrow(runHabitId);
        List<HabitHistory> habitHistories = runHabit.getHabitHistories();

        Integer totalStarCount = habitHistories.stream()
                .filter(habitHistory -> !habitHistory.getIsRest())
                .toList().size() * habitHistoryCommandService.COMPLETED_HISTORY_CREATION_REWARD_STAR_COUNT;
        Integer totalValue = habitHistories.stream()
                .filter(habitHistory -> !habitHistory.getIsRest())
                .mapToInt(HabitHistory::getRunValue)
                .sum();

        return HabitGetOneStatisticsResponse.builder()
                .totalStarCount(totalStarCount)
                .totalValue(totalValue)
                .build();
    }
}
