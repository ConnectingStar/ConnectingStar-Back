package connectingstar.tars.history.repository;


import com.querydsl.core.types.Order;
import connectingstar.tars.habit.request.HabitHistoryCreateCheckRequest;
import connectingstar.tars.habit.request.HabitHistoryGetListRequest;
import connectingstar.tars.habit.request.HabitHistoryListRequest;
import connectingstar.tars.habit.response.HabitHistoryDateGetResponseV1;
import connectingstar.tars.habit.response.HistoryCreateCheckResponse;
import connectingstar.tars.habit.response.HistoryListResponse;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.history.enums.HabitHistorySortBy;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 습관 기록 QueryDSL Repository
 */
public interface HabitHistoryRepositoryCustom {
    /**
     * 내 습관 기록 조회
     *
     * @param param 조회 조건
     * @return 조회 결과
     */
    public List<HabitHistoryDateGetResponseV1> getList(HabitHistoryGetListRequest param);

    /**
     * 습관 id와 휴식 여부로 습관 기록을 조회합니다.
     *
     * @param joinFields ("runHabit" | null) 조인할 필드. Fetch join 수행
     * @param offset     Pagination
     * @param limit      Pagination
     * @param orderBy    ("createdAt") Sort
     */
    public List<HabitHistory> findByRunHabitIdAndIsRest(
            Integer runHabitId,
            @Nullable Boolean isRest,
            @Nullable List<String> joinFields,
            @Nullable Integer offset,
            @Nullable Integer limit,
            @Nullable HabitHistorySortBy orderBy,
            @Nullable Order order
    );

    /**
     * 내 단일 습관 기록 조회
     *
     * @param param 조회 조건
     * @return 조회 결과
     */
    public HabitHistoryDateGetResponseV1 get(HabitHistoryListRequest param);

    /**
     * 기준 달의 습관 기록 조회
     *
     * @param param 조회 조건
     * @return 조회 결과
     */
    public List<HistoryListResponse> getMonthlyList(HabitHistoryListRequest param);

    /**
     * 기준 일의 주간 습관 기록 조회
     *
     * @param param 조회 조건
     * @return 조회 결과
     */
    public List<HistoryListResponse> getWeeklyList(HabitHistoryListRequest param,
                                                   LocalDateTime startDateTime, LocalDateTime endDateTime);


    public HistoryCreateCheckResponse getCheckTodayCreate(HabitHistoryCreateCheckRequest param);

    public void deleteByRunHabitId(Integer runHabitId);
}