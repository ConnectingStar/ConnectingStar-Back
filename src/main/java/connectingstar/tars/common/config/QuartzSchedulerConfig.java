package connectingstar.tars.common.config;

import connectingstar.tars.habit.job.HabitAlertSendJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz 관련 설정.
 * Quartz Job을 어떤 스케줄로 실행할 지 정보를 포함한다.
 *
 * @author 이우진
 */
@Configuration
public class QuartzSchedulerConfig {
    /**
     * 습관 알림 Job (HabitAlertSendJob)의 jobDetail 인스턴스
     */
    @Bean
    public JobDetail habitAlertSendJobDetail() {
        return JobBuilder.newJob().ofType(HabitAlertSendJob.class)
                .storeDurably()
                .withIdentity("Habit_Alert_Send_Job_Detail")
                .withDescription("습관 알림을 보내는 Job")
                .build();
    }

    /**
     * 습관 알림 JobDetail (HabitAlertSendJob) 이 실행될 스케줄을 설정합니다.
     * 매일 매 분 0초마다 실행. (12:00:00:00, 12:00:01:00 ...)
     */
    @Bean
    public Trigger habitAlertSendJobTrigger(JobDetail habitAlertSendJobDetail) {
        return TriggerBuilder.newTrigger().forJob(habitAlertSendJobDetail)
                .withIdentity("Habit_Alert_Send_Job_Trigger")
                .withDescription("습관 알림 Job을 매 분 0초마다 실행하는 트리거")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")) // 매 분 0초
                .build();
    }
}
