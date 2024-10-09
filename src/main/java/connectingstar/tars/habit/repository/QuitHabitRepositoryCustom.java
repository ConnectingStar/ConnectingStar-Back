package connectingstar.tars.habit.repository;

import com.querydsl.core.types.Order;
import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.enums.QuitHabitSortBy;
import connectingstar.tars.habit.response.QuitListResponse;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * 종료한 습관 QueryDSL Repository
 *
 * @author 김성수
 */
public interface QuitHabitRepositoryCustom {

    /**
     * 사용자 ID로 종료한 습관 리스트 조회
     */
    public List<QuitHabit> findByUserId(
            Integer userId,
            @Nullable Integer offset,
            @Nullable Integer limit,
            @Nullable QuitHabitSortBy orderBy,
            @Nullable Order order
    );

    /**
     * 종료한 습관 조회
     *
     * @return 조회 결과
     * @deprecated
     */
    @Deprecated
    public List<QuitListResponse> getList();
}