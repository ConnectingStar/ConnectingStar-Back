package connectingstar.tars.habit.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.habit.domain.QQuitHabit;
import connectingstar.tars.habit.domain.QRunHabit;
import connectingstar.tars.habit.request.QuitListRequest;
import connectingstar.tars.habit.request.RunListRequest;
import connectingstar.tars.habit.response.QuitListResponse;
import connectingstar.tars.habit.response.RunPutResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 진행중인 습관 Repository
 *
 * @author 김성수
 */

@RequiredArgsConstructor
@Repository
public class RunHabitDao {

  private final JPAQueryFactory queryFactory;

  /**
   * 진행중인 습관목록 조회
   *
   * @param param 조회 조건
   * @return 조회 결과
   */
  public List<RunPutResponse> getList(RunListRequest param) {
    QRunHabit runHabit = QRunHabit.runHabit;

    return queryFactory
        .select(getConstructorExpression())
        .from(runHabit)
        .where(runHabit.user.id.eq(param.getUserId()))
        .fetch();
  }

  private ConstructorExpression<RunPutResponse> getConstructorExpression() {
    QRunHabit runHabit = QRunHabit.runHabit;

    return Projections.constructor(
        RunPutResponse.class,
        runHabit,
        runHabit.alerts.get(0).alertTime,
        runHabit.alerts.get(1).alertTime
    );
  }
}
