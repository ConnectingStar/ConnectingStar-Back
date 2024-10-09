package connectingstar.tars.habit.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.habit.domain.QHabitAlert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HabitAlertRepositoryCustomImpl implements HabitAlertRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteByRunHabitId(Integer runHabitId) {
        QHabitAlert habitAlert = QHabitAlert.habitAlert;

        queryFactory.delete(habitAlert)
                .where(habitAlert.runHabit.runHabitId.eq(runHabitId))
                .execute();
    }
}
