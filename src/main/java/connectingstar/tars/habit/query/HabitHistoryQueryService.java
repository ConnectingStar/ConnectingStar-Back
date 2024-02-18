package connectingstar.tars.habit.query;

import connectingstar.tars.habit.repository.HabitHistoryDao;
import connectingstar.tars.habit.request.MonthHabitHistoryListRequest;
import connectingstar.tars.habit.response.MonthHabitHistoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 습관 기록 조회 서비스
 *
 * @author 김성수
 */

@Service
@RequiredArgsConstructor
public class HabitHistoryQueryService {

    private final HabitHistoryDao habitHistoryDao;

    /**
     * 월간 습관 기록 목록 조회
     *
     * @return 요청 결과
     */

    public List<MonthHabitHistoryListResponse> getMonthHabitHistoryList(MonthHabitHistoryListRequest param) {
        return habitHistoryDao.getMonthHistoryList(param);
    }

}