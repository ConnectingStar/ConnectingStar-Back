package connectingstar.tars.habit.query;

import connectingstar.tars.habit.repository.QuitHabitDao;
import connectingstar.tars.habit.request.QuitHabitListRequest;
import connectingstar.tars.habit.response.QuitHabitListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 종료 습관 조회 서비스
 *
 * @author 김성수
 */

@Service
@RequiredArgsConstructor
public class QuitHabitQueryService {

    private final QuitHabitDao quitHabitDao;

    /**
     * 종료 습관 목록 조회
     *
     * @return 요청 결과
     */

    public List<QuitHabitListResponse> getList(QuitHabitListRequest param) {
        return quitHabitDao.getList(param);
    }
}