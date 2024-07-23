package connectingstar.tars.habit.repository;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.habit.domain.QHabitHistory;
import connectingstar.tars.habit.domain.QRunHabit;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.RunHabitWithHistoryDto;
import connectingstar.tars.habit.request.RunListRequest;
import connectingstar.tars.habit.response.RunPutResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.NOT_USER_RUN_HABIT;

/**
 * 진행중인 습관 QueryDSL Repository
 *
 * @author 김성수
 */
@RequiredArgsConstructor
@Repository
@Slf4j
public class RunHabitRepositoryCustomImpl implements RunHabitRepositoryCustom {

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

    @Override
    public List<RunHabitWithHistoryDto> getListOfUserWithHistoryByDate(Integer userId, LocalDate date) {
        QRunHabit runHabit = QRunHabit.runHabit;
        QHabitHistory habitHistory = QHabitHistory.habitHistory;
        
        return queryFactory
                .select(Projections.constructor(RunHabitWithHistoryDto.class,
                        runHabit,
                        habitHistory))
                .from(runHabit)
                .where(runHabit.user.id.eq(userId))
                .leftJoin(runHabit.habitHistories, habitHistory)
                .on(habitHistory.runDate.between(date.atStartOfDay(), date.atTime(LocalTime.MAX)))
                .fetch();
    }

    /**
     * 진행중인 습관 해당유저 소유여부 확인
     *
     * @param runHabitId 진행중인 습관 ID
     * @return 유저 소유여부(boolean)
     */
    public RunHabit checkUserId(Integer runHabitId) {
        QRunHabit runHabit = QRunHabit.runHabit;

        return queryFactory
                .select(runHabit)
                .from(runHabit)
                .where(runHabit.runHabitId.eq(runHabitId))
                .fetch()
                .stream()
                .filter(runHabit1 -> runHabit1.getUser().getId().equals(UserUtils.getUserId()))
                .findFirst()
                .orElseThrow(() -> new ValidationException(NOT_USER_RUN_HABIT));
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
