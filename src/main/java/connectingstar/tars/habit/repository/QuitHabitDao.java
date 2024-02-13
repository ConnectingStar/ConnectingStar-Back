package connectingstar.tars.habit.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.constellation.domain.QConstellation;
import connectingstar.tars.constellation.response.ConstellationListResponse;
import connectingstar.tars.habit.domain.QQuitHabit;
import connectingstar.tars.habit.request.QuitHabitListRequest;
import connectingstar.tars.habit.response.QuitHabitListResponse;
import connectingstar.tars.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static connectingstar.tars.user.domain.QUser.user;

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
     * @param param 조회 조건
     * @return      조회 결과
     */

    public List<QuitHabitListResponse> getList(QuitHabitListRequest param) {
        QQuitHabit quitHabit = QQuitHabit.quitHabit;
        // 타입별 회원이 보유중인 별자리 조회
            return queryFactory
                    .select(getConstructorExpression())
                    .from(quitHabit)
                    .where(quitHabit.user.Id.eq(param.getUserId()))
                    .fetch();
    }

    private ConstructorExpression<QuitHabitListResponse> getConstructorExpression() {
        QQuitHabit quitHabit = QQuitHabit.quitHabit;

        return Projections.constructor(
                QuitHabitListResponse.class,
                quitHabit.quitHabitId,
                quitHabit.user.Id,
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
