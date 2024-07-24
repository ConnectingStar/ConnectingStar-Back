package connectingstar.tars.habit.repository;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.RunHabitAndHistoryDto;
import connectingstar.tars.habit.request.RunListRequest;
import connectingstar.tars.habit.response.RunPutResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * 진행중인 습관 QueryDSL Repository
 *
 * @author 김성수
 */
public interface RunHabitRepositoryCustom {
    public List<RunPutResponse> getList(RunListRequest param);

    /**
     * 유저의 모든 RunHabit을 조회합니다.
     * 날짜가 일치하는 HabitHistory를 조인해서 반환합니다.
     */
    public List<RunHabitAndHistoryDto> getListOfUserWithHistoryByDate(Integer userId, LocalDate date);

    /**
     * 진행중인 습관 해당유저 소유여부 확인
     *
     * @param runHabitId 진행중인 습관 ID
     * @return 유저 소유여부(boolean)
     */
    public RunHabit checkUserId(Integer runHabitId);
}
