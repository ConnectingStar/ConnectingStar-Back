package connectingstar.tars.history.repository;


import connectingstar.tars.habit.request.HabitHistoryCreateCheckRequest;
import connectingstar.tars.habit.request.HabitHistoryGetListRequest;
import connectingstar.tars.habit.request.HabitHistoryListRequest;
import connectingstar.tars.habit.response.HabitHistoryDateGetResponseV1;
import connectingstar.tars.habit.response.HistoryCreateCheckResponse;
import connectingstar.tars.habit.response.HistoryListResponse;

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
}