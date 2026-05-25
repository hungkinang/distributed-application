package edu.bookingtour.svc.email.config;

import edu.bookingtour.svc.email.job.OutboxRetryJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    JobDetail outboxRetryJobDetail() {
        return JobBuilder.newJob(OutboxRetryJob.class)
                .withIdentity("outboxRetryJob")
                .storeDurably()
                .build();
    }

    @Bean
    Trigger outboxRetryTrigger(JobDetail outboxRetryJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(outboxRetryJobDetail)
                .withIdentity("outboxRetryTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */2 * * * ?"))
                .build();
    }
}
