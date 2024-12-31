package connectingstar.tars.history.query;

import com.querydsl.core.types.Order;
import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.query.RunHabitQueryService;
import connectingstar.tars.habit.request.HabitHistoryCreateCheckRequest;
import connectingstar.tars.habit.request.HabitHistoryGetListRequest;
import connectingstar.tars.habit.request.HabitHistoryListRequest;
import connectingstar.tars.habit.response.HabitHistoryDateGetResponseV1;
import connectingstar.tars.habit.response.HistoryCreateCheckResponse;
import connectingstar.tars.habit.response.HistoryListResponse;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.history.mapper.HabitHistoryMapper;
import connectingstar.tars.history.repository.HabitHistoryRepository;
import connectingstar.tars.history.repository.HabitHistoryRepositoryCustom;
import connectingstar.tars.history.request.HistoryGetListRequestParam;
import connectingstar.tars.history.request.param.HistoryGetOneRequestParam;
import connectingstar.tars.history.response.HistoryGetListResponse;
import connectingstar.tars.history.response.HistoryGetOneResponse;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static connectingstar.tars.common.exception.errorcode.HistoryErrorCode.HISTORY_CANNOT_ACCESS;
import static connectingstar.tars.common.exception.errorcode.HistoryErrorCode.HISTORY_NOT_FOUND;

/**
 * 습관 기록 조회 서비스
 *
 * @author 김성수
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitHistoryQueryService {

    public static final int DAYS_TO_ADD = 6;
    private final HabitHistoryRepository habitHistoryRepository;
    private final HabitHistoryRepositoryCustom habitHistoryRepositoryCustom;

    private final UserQueryService userQueryService;
    private final RunHabitQueryService runHabitQueryService;

    private final HabitHistoryMapper habitHistoryMapper;

    private final ConversionService conversionService;

    /**
     * 내 습관 기록 1건을 id로 조회합니다.
     *
     * @param requestParam - ("runHabit") .related - 같이 조회(JOIN)할 필드.
     * @return
     */
    public HistoryGetOneResponse getMineById(Integer habitHistoryId, HistoryGetOneRequestParam requestParam) {
        HabitHistory habitHistory = habitHistoryRepository.findById(habitHistoryId)
                .orElseThrow(() -> new ValidationException(HISTORY_NOT_FOUND));

        User user = userQueryService.getCurrentUserOrElseThrow();

        if (!habitHistory.getUser().getId().equals(user.getId())) {
            throw new ValidationException(HISTORY_CANNOT_ACCESS);
        }

        RunHabit runHabit = null;

        // param 'related' 필드 조회
        if (requestParam.getRelated() != null) {
            for (String relatedField : requestParam.getRelated()) {
                switch (relatedField) {
                    case "runHabit":
                        runHabit = habitHistory.getRunHabit();
                        break;
                }
            }
        }

        return habitHistoryMapper.toGetOneResponse(habitHistory, runHabit);
    }

    public HistoryGetListResponse getMyListByRunHabitId(HistoryGetListRequestParam requestParam) {
        RunHabit runHabit = runHabitQueryService.getMineByIdOrElseThrow(requestParam.getRunHabitId());

        List<HabitHistory> habitHistories = habitHistoryRepositoryCustom.findByRunHabitIdAndIsRest(
                requestParam.getRunHabitId(),
                requestParam.getIsRest(),
                requestParam.getRunDate(),
                toJoinFields(requestParam.getRelated()),
                requestParam.getPage(),
                requestParam.getSize(),
                requestParam.getSortBy(),
                conversionService.convert(requestParam.getSortOrder(), Order.class)
        );

        return HistoryGetListResponse.builder()
                .histories(habitHistories.stream()
                        .map(habitHistory ->
                                habitHistoryMapper.toDto(habitHistory, habitHistory.getRunHabit())
                        )
                        .toList()
                )
                .build();
    }

    private List<String> toJoinFields(List<HistoryGetListRequestParam.Related> related) {
        if (related == null) {
            return null;
        }

        List<String> joinFields = related.stream()
                .map(
                        relatedField -> {
                            switch (relatedField) {
                                case RUN_HABIT:
                                    return "runHabit";
                                default:
                                    return null;
                            }
                        }
                )
                .filter(joinField -> joinField != null)
                .toList();

        return joinFields;
    }

    /**
     * 월간 습관 기록 목록 조회
     *
     * @param param 습관월간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    public List<HistoryListResponse> getMonthList(HabitHistoryListRequest param) {
        return habitHistoryRepositoryCustom.getMonthlyList(param);
    }

    /**
     * 단일 습관 기록 목록 조회
     *
     * @param param 습관주간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     */
    public HabitHistoryDateGetResponseV1 getByDate(HabitHistoryListRequest param) {
        return habitHistoryRepositoryCustom.get(param);
    }

    /**
     * 주간 습관 기록 목록 조회
     *
     * @param param 습관주간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    public List<HistoryListResponse> getWeeklyList(HabitHistoryListRequest param) {
        LocalDate referenceDate = param.getReferenceDate();
        LocalDate thisWeekSunday = referenceDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime startDateTime = thisWeekSunday.atStartOfDay();
        LocalDate thisWeekSat = thisWeekSunday.plusDays(DAYS_TO_ADD);
        LocalDateTime endDateTime = thisWeekSat.atTime(23, 59, 59);
        return habitHistoryRepositoryCustom.getWeeklyList(param, startDateTime, endDateTime);
    }

    /**
     * 습관 기록 목록 조회
     *
     * @param param 습관기록목록 조회를 위한 위한 진행중인 습관 ID, 최신,오래된 순 구분, 휴식 여부 구분
     * @return 배열(습관 수행 날짜, 실천한 장소, 실천량, 단위, 느낀점)
     */
    public List<HabitHistoryDateGetResponseV1> getList(HabitHistoryGetListRequest param) {
        return habitHistoryRepositoryCustom.getList(param);
    }

    public HistoryCreateCheckResponse checkTodayCreate(HabitHistoryCreateCheckRequest param) {
        return habitHistoryRepositoryCustom.getCheckTodayCreate(param);
    }
}