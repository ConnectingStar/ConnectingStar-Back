package connectingstar.tars.habit.query;

import connectingstar.tars.habit.dto.HabitAlertWithDevice;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

/**
 * 습관 알림 조회 Service
 *
 * @author 이우진
 */
@Service
@RequiredArgsConstructor
public class HabitAlertQueryService {
    private final HabitAlertRepository habitAlertRepository;

    /**
     * 입력으로 받은 알림 시각과 시, 분 정보가 일치하는 습관 알림 리스트를 반환합니다.
     *
     * @example
     * alertTime = 2024.07.01 12:01:02
     * -> alertTime이 12:01:00 ~ 12:01:59 범위에 해당하는 habit alert 리스트
     *
     * @author 이우진
     */
    public List<HabitAlertWithDevice> getListByAlertTimeMinuteWithRunHabitAndDevice(LocalTime alertTime) {
        LocalTime startTime = alertTime.withSecond(0).withNano(0);
        LocalTime endTime = startTime.plusMinutes(1).minusNanos(1);

        return habitAlertRepository.findByAlertTimeBetweenWithRunHabitAndDevice(startTime, endTime);
    }
}
