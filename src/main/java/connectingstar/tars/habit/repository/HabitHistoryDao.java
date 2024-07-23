package connectingstar.tars.habit.repository;


import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.habit.domain.QHabitHistory;
import connectingstar.tars.habit.domain.QRunHabit;
import connectingstar.tars.habit.request.HabitHistoryCreateCheckRequest;
import connectingstar.tars.habit.request.HabitHistoryGetListRequest;
import connectingstar.tars.habit.request.HabitHistoryListRequest;
import connectingstar.tars.habit.response.HabitHistoryDateGetResponseV1;
import connectingstar.tars.habit.response.HistoryCreateCheckResponse;
import connectingstar.tars.habit.response.HistoryListResponse;
import connectingstar.tars.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    private static BooleanExpression isSameDate(HabitHistoryCreateCheckRequest param,
                                                QHabitHistory habitHistory) {
        return habitHistory.runDate.year().eq(param.getReferenceDate().getYear())
                .and(habitHistory.runDate.month().eq(param.getReferenceDate().getMonthValue()))
                .and(habitHistory.runDate.dayOfMonth().eq(param.getReferenceDate().getDayOfMonth()));
    }

    /**
     * 내 습관 기록 조회
     *
     * @param param 조회 조건
     * @return 조회 결과
     */
    public List<HabitHistoryDateGetResponseV1> getList(HabitHistoryGetListRequest param) {

        QHabitHistory habitHistory = QHabitHistory.habitHistory;
        OrderSpecifier<Integer> orderSpecifier = habitHistory.habitHistoryId.desc();
        if (param.getIncrease()) {
            orderSpecifier = habitHistory.habitHistoryId.asc();
        }
        return queryFactory
                .select(Projections.constructor(
                        HabitHistoryDateGetResponseV1.class,
                        habitHistory.runDate,
                        habitHistory.runPlace,
                        habitHistory.runValue,
                        habitHistory.runHabit.unit,
                        habitHistory.review
                ))
                .from(habitHistory)
                .where(getPredicate(param, habitHistory))
                .orderBy(orderSpecifier)
                .fetch();
    }

    /**
     * 내 단일 습관 기록 조회
     *
     * @param param 조회 조건
     * @return 조회 결과
     */
    public HabitHistoryDateGetResponseV1 get(HabitHistoryListRequest param) {

        QHabitHistory habitHistory = QHabitHistory.habitHistory;

        return queryFactory
                .select(Projections.constructor(
                        HabitHistoryDateGetResponseV1.class,
                        habitHistory.runDate,
                        habitHistory.runPlace,
                        habitHistory.runValue,
                        habitHistory.runHabit.unit,
                        habitHistory.review
                ))
                .from(habitHistory)
                .where(getPredicate(param, habitHistory))
                .fetchFirst();
    }

    /**
     * 기준 달의 습관 기록 조회
     *
     * @param param 조회 조건
     * @return 조회 결과
     */
    public List<HistoryListResponse> getMonthlyList(HabitHistoryListRequest param) {

        QHabitHistory habitHistory = QHabitHistory.habitHistory;

        return queryFactory
                .select(getConstructorExpression())
                .from(habitHistory)
                .where(getPredicate(param, habitHistory))
                .fetch();
    }

    /**
     * 기준 일의 주간 습관 기록 조회
     *
     * @param param 조회 조건
     * @return 조회 결과
     */
    public List<HistoryListResponse> getWeeklyList(HabitHistoryListRequest param,
                                                   LocalDateTime startDateTime, LocalDateTime endDateTime) {

        QHabitHistory habitHistory = QHabitHistory.habitHistory;

        return queryFactory
                .select(getConstructorExpression())
                .from(habitHistory)
                .where(getPredicate(param, habitHistory, startDateTime, endDateTime))
                .fetch();
    }


    public HistoryCreateCheckResponse getCheckTodayCreate(HabitHistoryCreateCheckRequest param) {
        QUser user = QUser.user;
        QHabitHistory habitHistory = QHabitHistory.habitHistory;
        QRunHabit runHabit = QRunHabit.runHabit;

        BooleanExpression userMatch = user.id.eq(UserUtils.getUserId());
        BooleanExpression runHabitMatch = runHabit.runHabitId.eq(param.getRunHabitId());
        BooleanExpression dateMatch = isSameDate(param, habitHistory);
        return new HistoryCreateCheckResponse(queryFactory
                .selectFrom(habitHistory)
                .join(habitHistory.user, user)
                .join(habitHistory.runHabit, runHabit)
                .where(userMatch.and(runHabitMatch).and(dateMatch))
                .fetchCount() > 0);
    }

    private BooleanExpression getPredicate(HabitHistoryGetListRequest param,
                                           QHabitHistory habitHistory) {
        if (param.getIsRest() != null) {
            return habitHistory.user.id.eq(UserUtils.getUserId())
                    .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()))
                    .and(habitHistory.isRest.eq(param.getIsRest()));
        }
        return habitHistory.user.id.eq(UserUtils.getUserId())
                .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()));
    }

    private BooleanExpression getPredicate(HabitHistoryListRequest param,
                                           QHabitHistory habitHistory) {
        return habitHistory.user.id.eq(UserUtils.getUserId())
                .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()))
                .and(habitHistory.runDate.year().eq(param.getReferenceDate().getYear()))
                .and(habitHistory.runDate.month().eq(param.getReferenceDate().getMonthValue()));
    }

    private BooleanExpression getPredicate(HabitHistoryListRequest param, QHabitHistory habitHistory,
                                           LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return habitHistory.user.id.eq(UserUtils.getUserId())
                .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()))
                .and(habitHistory.runDate.between(startDateTime, endDateTime));
    }

    private ConstructorExpression<HistoryListResponse> getConstructorExpression() {
        QHabitHistory habitHistory = QHabitHistory.habitHistory;

        return Projections.constructor(
                HistoryListResponse.class,
                habitHistory.runDate,
                habitHistory.achievement,
                habitHistory.runValue
        );
    }
}