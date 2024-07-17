package connectingstar.tars.habit.query;

import connectingstar.tars.habit.repository.HabitHistoryDao;
import connectingstar.tars.habit.request.HabitHistoryCreateCheckRequest;
import connectingstar.tars.habit.request.HabitHistoryGetListRequest;
import connectingstar.tars.habit.request.HabitHistoryListRequest;
import connectingstar.tars.habit.response.HistoryCreateCheckResponse;
import connectingstar.tars.habit.response.HistoryGetListResponse;
import connectingstar.tars.habit.response.HistoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * 습관 기록 조회 서비스
 *
 * @author 김성수
 */

@Service
@RequiredArgsConstructor
public class HabitHistoryQueryService {

    public static final int DAYS_TO_ADD = 6;
    private final HabitHistoryDao habitHistoryDao;

    /**
     * 월간 습관 기록 목록 조회
     *
     * @param param 습관월간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    public List<HistoryListResponse> getMonthList(HabitHistoryListRequest param) {
        return habitHistoryDao.getMonthlyList(param);
    }

    /**
     * 단일 습관 기록 목록 조회
     *
     * @param param 습관주간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    public HistoryGetListResponse get(HabitHistoryListRequest param) {
        return habitHistoryDao.get(param);
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
        return habitHistoryDao.getWeeklyList(param, startDateTime, endDateTime);
    }

    /**
     * 습관 기록 목록 조회
     *
     * @param param 습관기록목록 조회를 위한 위한 진행중인 습관 ID, 최신,오래된 순 구분, 휴식 여부 구분
     * @return 배열(습관 수행 날짜, 실천한 장소, 실천량, 단위, 느낀점)
     */
    public List<HistoryGetListResponse> getList(HabitHistoryGetListRequest param) {
        return habitHistoryDao.getList(param);
    }

    public HistoryCreateCheckResponse checkTodayCreate(HabitHistoryCreateCheckRequest param) {
        return habitHistoryDao.getCheckTodayCreate(param);
    }


}