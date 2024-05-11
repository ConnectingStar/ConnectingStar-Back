package connectingstar.tars.habit.query;

import connectingstar.tars.habit.repository.QuitHabitDao;
import connectingstar.tars.habit.response.QuitListResponse;
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
     * @return 배열(종료한 습관 ID, 사용자 PK, 사용자 이름, 실천 시간, 장소, 행동, 실천횟수, 휴식 실천횟수, 종료 사유, 시작 날짜, 종료 날짜)
     */
    public List<QuitListResponse> getList() {
        return quitHabitDao.getList();
    }
}