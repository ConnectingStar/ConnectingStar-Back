package connectingstar.tars.habit.query;

import connectingstar.tars.habit.repository.HabitHistoryDao;
import connectingstar.tars.habit.request.HabitHistoryGetListRequest;
import connectingstar.tars.habit.request.HabitHistoryListRequest;
import connectingstar.tars.habit.response.HabitHistoryGetListResponse;
import connectingstar.tars.habit.response.HabitHistoryListResponse;
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
     * @return 요청 결과
     */

    public List<HabitHistoryListResponse> getMonthHabitHistoryList(HabitHistoryListRequest param) {
        return habitHistoryDao.getMonthHabitHistoryList(param);
    }

    /**
     * 주간 습관 기록 목록 조회
     *
     * @return 요청 결과
     */

    public List<HabitHistoryListResponse> getWeeklyHabitHistoryList(HabitHistoryListRequest param) {
        LocalDate referenceDate = param.getReferenceDate();
        LocalDate thisWeekSunday = referenceDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime startDateTime = thisWeekSunday.atStartOfDay();
        LocalDate thisWeekSat = thisWeekSunday.plusDays(DAYS_TO_ADD);
        LocalDateTime endDateTime = thisWeekSat.atTime(23, 59, 59);
        return habitHistoryDao.getWeeklyHabitHistoryList(param,startDateTime,endDateTime);
    }

    /**
     * 습관 기록 목록 조회
     *
     * @return 요청 결과
     */

    public List<HabitHistoryGetListResponse> getHabitHistoryList(HabitHistoryGetListRequest param) {
        return habitHistoryDao.getHabitHistoryList(param);
    }

}