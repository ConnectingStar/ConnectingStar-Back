package connectingstar.tars.habit.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.habit.domain.QQuitHabit;
import connectingstar.tars.habit.domain.QuitHabit;
import connectingstar.tars.habit.response.QuitListResponse;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 종료한 습관 Repository
 *
 * @author 김성수
 */

@RequiredArgsConstructor
@Repository
public class QuitHabitRepositoryCustomImpl implements QuitHabitRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<QuitHabit> findByUserId(
            Integer userId,
            @Nullable Integer offset,
            @Nullable Integer limit
    ) {
        QQuitHabit quitHabit = QQuitHabit.quitHabit;

        JPAQuery<QuitHabit> query = queryFactory
                .selectFrom(quitHabit)
                .where(quitHabit.user.id.eq(userId));

        if (offset != null) {
            query = query.offset(offset);
        }

        if (limit != null) {
            query = query.limit(limit);
        }

        return query.fetch();
    }

    /**
     * 종료한 습관 조회
     *
     * @return 조회 결과
     * @deprecated
     */
    @Deprecated
    public List<QuitListResponse> getList() {
        QQuitHabit quitHabit = QQuitHabit.quitHabit;

        return queryFactory
                .select(getConstructorExpression())
                .from(quitHabit)
                .where(quitHabit.user.id.eq(UserUtils.getUserId()))
                .fetch();
    }

    private ConstructorExpression<QuitListResponse> getConstructorExpression() {
        QQuitHabit quitHabit = QQuitHabit.quitHabit;

        return Projections.constructor(
                QuitListResponse.class,
                quitHabit.quitHabitId,
                quitHabit.user.id,
                quitHabit.user.nickname,
                quitHabit.runTime,
                quitHabit.place,
                quitHabit.action,
                quitHabit.completedHistoryCount,
                quitHabit.unit,
                quitHabit.restHistoryCount,
                quitHabit.reasonOfQuit,
                quitHabit.startDate,
                quitHabit.quitDate
        );
    }
}