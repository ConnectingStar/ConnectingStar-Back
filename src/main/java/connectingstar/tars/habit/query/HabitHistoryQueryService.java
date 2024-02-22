package connectingstar.tars.habit.query;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.common.exception.errorcode.HabitErrorCode;
import connectingstar.tars.common.exception.errorcode.UserErrorCode;
import connectingstar.tars.habit.domain.HabitHistory;
import connectingstar.tars.habit.repository.HabitHistoryDao;
import connectingstar.tars.habit.request.HabitHistoryCreateCheckRequest;
import connectingstar.tars.habit.request.HabitHistoryGetListRequest;
import connectingstar.tars.habit.request.HabitHistoryListRequest;
import connectingstar.tars.habit.response.HabitHistoryCreateCheckResponse;
import connectingstar.tars.habit.response.HabitHistoryGetListResponse;
import connectingstar.tars.habit.response.HabitHistoryListResponse;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public List<HabitHistoryListResponse> getMonthList(HabitHistoryListRequest param) {
        return habitHistoryDao.getMonthHabitHistoryList(param);
    }

    /**
     * 주간 습관 기록 목록 조회
     *
     * @return 요청 결과
     */

    public List<HabitHistoryListResponse> getWeeklyList(HabitHistoryListRequest param) {
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

    public List<HabitHistoryGetListResponse> getList(HabitHistoryGetListRequest param) {
        return habitHistoryDao.getHabitHistoryList(param);
    }

    public HabitHistoryCreateCheckResponse checkTodayCreate(HabitHistoryCreateCheckRequest param){
        return habitHistoryDao.getCheckTodayCreate(param);
    }


}