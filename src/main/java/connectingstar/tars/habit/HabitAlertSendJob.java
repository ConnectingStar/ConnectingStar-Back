package connectingstar.tars.habit;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 습관 알림을 전송하는 Quartz Job.
 * Quartz Config에서 지정한 스케줄에 따라 실행한다.
 *
 * @author 이우진
 */
@Component
@Slf4j
public class HabitAlertSendJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("습관 알림 Job 실행" + new Date());
    }
}
