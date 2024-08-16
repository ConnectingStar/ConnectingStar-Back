package connectingstar.tars.habit.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import connectingstar.tars.habit.repository.RunHabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.ALERT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class HabitAlertQueryService {
    private final HabitAlertRepository habitAlertRepository;
    private final RunHabitRepository runHabitRepository;

    /**
     * 사용자 ID와 알림 순서로 알림을 조회한다.
     * 없을 시 IllegalArgumentException 발생
     */
    public HabitAlert getByRunHabitIdAndOrderOrElseThrow(Integer runHabitId, Integer alertOrder) {
        RunHabit runHabit = runHabitRepository.getReferenceById(runHabitId);

        return habitAlertRepository.findByRunHabitAndAlertOrder(runHabit, alertOrder)
                .orElseThrow(() -> new ValidationException(ALERT_NOT_FOUND));
    }
}
