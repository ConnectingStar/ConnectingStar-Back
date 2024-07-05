package connectingstar.tars.habit;

import connectingstar.tars.habit.dto.HabitAlertWithDevice;
import connectingstar.tars.habit.query.HabitAlertQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 습관 알림을 전송하는 Quartz Job.
 * Quartz Config에서 지정한 스케줄에 따라 실행한다.
 *
 * @author 이우진
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HabitAlertSendJob implements Job {
    private final HabitAlertQueryService habitAlertQueryService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 일정에 맞는 habit alert fetch
        Date firedTime = jobExecutionContext.getFireTime();
        LocalTime firedLocalTime = LocalTime.ofInstant(firedTime.toInstant(), ZoneId.systemDefault());

        List<HabitAlertWithDevice> habitAlertWithDevices = habitAlertQueryService.getListByAlertTimeMinuteWithUserAndRunHabitAndDevice(firedLocalTime);

        // fcm 토큰 획득
        // 메세지 내용 생성

        // 전송
    }
}
