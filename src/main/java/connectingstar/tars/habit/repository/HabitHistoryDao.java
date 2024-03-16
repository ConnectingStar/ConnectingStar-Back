package connectingstar.tars.habit.repository;


import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.habit.domain.QHabitHistory;
import connectingstar.tars.habit.domain.QRunHabit;
import connectingstar.tars.habit.request.HabitHistoryCreateCheckRequest;
import connectingstar.tars.habit.request.HabitHistoryGetListRequest;
import connectingstar.tars.habit.request.HabitHistoryListRequest;
import connectingstar.tars.habit.response.HabitHistoryCreateCheckResponse;
import connectingstar.tars.habit.response.HabitHistoryGetListResponse;
import connectingstar.tars.habit.response.HabitHistoryListResponse;
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


    /**
     * 내 습관 기록 조회
     *
     * @param param 조회 조건
     * @return      조회 결과
     */
    public List<HabitHistoryGetListResponse> getHabitHistoryList(HabitHistoryGetListRequest param){

        QHabitHistory habitHistory = QHabitHistory.habitHistory;
        OrderSpecifier<Integer> orderSpecifier = habitHistory.habitHistoryId.desc();
        if(param.getIncrease())  orderSpecifier = habitHistory.habitHistoryId.asc();
        return queryFactory
                .select(Projections.constructor(
                        HabitHistoryGetListResponse.class,
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
     * 기준 달의 습관 기록 조회
     *
     * @param param 조회 조건
     * @return      조회 결과
     */
    public List<HabitHistoryListResponse> getMonthHabitHistoryList(HabitHistoryListRequest param) {

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
     * @return      조회 결과
     */
    public List<HabitHistoryListResponse> getWeeklyHabitHistoryList(HabitHistoryListRequest param, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        QHabitHistory habitHistory = QHabitHistory.habitHistory;

        return queryFactory
                .select(getConstructorExpression())
                .from(habitHistory)
                .where(getPredicate(param, habitHistory,startDateTime,endDateTime))
                .fetch();
    }

    public HabitHistoryCreateCheckResponse getCheckTodayCreate(HabitHistoryCreateCheckRequest param) {
        QUser user = QUser.user;
        QHabitHistory habitHistory = QHabitHistory.habitHistory;
        QRunHabit runHabit = QRunHabit.runHabit;

        BooleanExpression userMatch = user.Id.eq(param.getUserId());
        BooleanExpression runHabitMatch = runHabit.runHabitId.eq(param.getRunHabitId());
        BooleanExpression dateMatch = isSameDate(param, habitHistory);
        return new HabitHistoryCreateCheckResponse(queryFactory
                .selectFrom(habitHistory)
                .join(habitHistory.user, user)
                .join(habitHistory.runHabit, runHabit)
                .where(userMatch.and(runHabitMatch).and(dateMatch))
                .fetchCount() > 0);
    }

    private static BooleanExpression isSameDate(HabitHistoryCreateCheckRequest param, QHabitHistory habitHistory) {
        return habitHistory.runDate.year().eq(param.getDate().getYear())
                .and(habitHistory.runDate.month().eq(param.getDate().getMonthValue()))
                .and(habitHistory.runDate.dayOfMonth().eq(param.getDate().getDayOfMonth()));
    }

    private BooleanExpression getPredicate(HabitHistoryGetListRequest param, QHabitHistory habitHistory) {
        if(param.getIsRest() != null) return habitHistory.user.Id.eq(param.getUserId())
                .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()))
                .and(habitHistory.isRest.eq(param.getIsRest()));
        return habitHistory.user.Id.eq(param.getUserId())
                .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()));
    }

    private BooleanExpression getPredicate(HabitHistoryListRequest param, QHabitHistory habitHistory) {
        return habitHistory.user.Id.eq(param.getUserId())
                .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()))
                .and(habitHistory.createdAt.year().eq(param.getReferenceDate().getYear()))
                .and(habitHistory.createdAt.month().eq(param.getReferenceDate().getMonthValue()));
    }

    private BooleanExpression getPredicate(HabitHistoryListRequest param, QHabitHistory habitHistory,LocalDateTime startDateTime,LocalDateTime endDateTime) {
        return habitHistory.user.Id.eq(param.getUserId())
                .and(habitHistory.runHabit.runHabitId.eq(param.getRunHabitId()))
                .and(habitHistory.createdAt.between(startDateTime,endDateTime));
    }

    private ConstructorExpression<HabitHistoryListResponse> getConstructorExpression() {
        QHabitHistory habitHistory = QHabitHistory.habitHistory;

        return Projections.constructor(
                HabitHistoryListResponse.class,
                habitHistory.runDate,
                habitHistory.achievement,
                habitHistory.runValue
        );
    }
}