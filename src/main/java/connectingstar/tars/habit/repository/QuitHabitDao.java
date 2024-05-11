package connectingstar.tars.habit.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.habit.domain.QQuitHabit;
import connectingstar.tars.habit.response.QuitListResponse;
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
public class QuitHabitDao {

    private final JPAQueryFactory queryFactory;

    /**
     * 종료한 습관 조회
     *
     * @return 조회 결과
     */
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
                quitHabit.value,
                quitHabit.restValue,
                quitHabit.reasonOfQuit,
                quitHabit.startDate,
                quitHabit.quitDate
        );
    }
}