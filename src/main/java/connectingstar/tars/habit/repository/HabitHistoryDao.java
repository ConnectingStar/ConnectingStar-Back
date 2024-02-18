package connectingstar.tars.habit.repository;


import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.habit.domain.QHabitHistory;
import connectingstar.tars.habit.request.MonthHabitHistoryListRequest;
import connectingstar.tars.habit.response.MonthHabitHistoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 습관 기록 Repository
 *
 * @author 김성수
 */

@RequiredArgsConstructor
@Repository
public class HabitHistoryDao {

    private final JPAQueryFactory queryFactory;

    /**
     * 기준 달의 습관 조회
     *
     * @param param 조회 조건
     * @return      조회 결과
     */
    public List<MonthHabitHistoryListResponse> getMonthHistoryList(MonthHabitHistoryListRequest param) {

        QHabitHistory habitHistory = QHabitHistory.habitHistory;

        return queryFactory
                .select(getConstructorExpression())
                .from(habitHistory)
                .where(getPredicate(param, habitHistory))
                .fetch();
    }

    private BooleanExpression getPredicate(MonthHabitHistoryListRequest param, QHabitHistory habitHistory) {
        return habitHistory.user.Id.eq(param.getUserId())
                .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()))
                .and(habitHistory.createdAt.year().eq(param.getReferenceDate().getYear()))
                .and(habitHistory.createdAt.month().eq(param.getReferenceDate().getMonthValue()));
    }

    private ConstructorExpression<MonthHabitHistoryListResponse> getConstructorExpression() {
        QHabitHistory habitHistory = QHabitHistory.habitHistory;

        return Projections.constructor(
                MonthHabitHistoryListResponse.class,
                habitHistory.runDate,
                habitHistory.achievement,
                habitHistory.runValue
        );
    }
}