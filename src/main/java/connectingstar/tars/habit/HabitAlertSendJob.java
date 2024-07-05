package connectingstar.tars.habit;

import connectingstar.tars.habit.dto.HabitAlertWithDevice;
import connectingstar.tars.habit.query.HabitAlertQueryService;
import connectingstar.tars.pushnotification.command.PushNotificationCommandService;
import connectingstar.tars.pushnotification.dto.PushNotificationMessage;
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
@RequiredArgsConstructor
@Slf4j
public class HabitAlertSendJob implements Job {
    private final HabitAlertQueryService habitAlertQueryService;
    private final PushNotificationCommandService pushNotificationCommandService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 일정에 맞는 habit alert fetch
        Date firedTime = jobExecutionContext.getFireTime();
        LocalTime firedLocalTime = LocalTime.ofInstant(firedTime.toInstant(), ZoneId.systemDefault());

        List<HabitAlertWithDevice> habitAlertWithDevices = habitAlertQueryService.getListByAlertTimeMinuteWithUserAndRunHabitAndDevice(firedLocalTime);

        if (habitAlertWithDevices == null || habitAlertWithDevices.isEmpty()) {
            return;
        }

        // 메세지 내용 생성
        List<PushNotificationMessage> pushNotificationMessages = habitAlertWithDevices.stream()
                .map(habitAlertWithDevice -> habitAlertQueryService.generatePushNotificationMessage(habitAlertWithDevice))
                .toList();

        // 전송
        try {
            pushNotificationCommandService.sendMany(pushNotificationMessages);
        } catch (Exception exception) {
            log.error(exception.toString());
        }
    }
}
