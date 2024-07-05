package connectingstar.tars.habit.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 기록 독려 알림을 전송하는 Quartz Job.
 * Quartz Config에서 지정한 스케줄에 따라 실행한다.
 *
 * @author 이우진
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MissingHabitHistoryAlertSendJob implements Job {
    /**
     * 오늘 습관 기록을 안 하면 다음 날 저녁 6시에 미기록 알림을 전송한다.
     * => 저녁 6시마다 runDate가 어제인 history가 없는 습관을 fetch한 뒤 해당되는 유저에게 알림을 전송한다.
     *
     * @see <a href="https://www.figma.com/design/deVOGLOqzbCjKJP9fDeB3i/%ED%95%B4%EB%B9%97%EB%B2%84%EB%94%94?node-id=4076-4527&t=l6GqwDfX0aWYgvoc-4">Figma</a>
     * @author 이우진
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //
    }
}
