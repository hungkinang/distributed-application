package edu.bookingtour.svc.email.job;

import edu.bookingtour.svc.email.domain.EmailOutbox;
import edu.bookingtour.svc.email.repo.EmailOutboxRepository;
import edu.bookingtour.svc.email.service.MailAsyncExecutor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

/** Quartz cron — retry email PENDING (background / đa luồng qua thread pool). */
@Component
@DisallowConcurrentExecution
public class OutboxRetryJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(OutboxRetryJob.class);

    private final EmailOutboxRepository outboxRepository;
    private final MailAsyncExecutor mailAsyncExecutor;

    public OutboxRetryJob(EmailOutboxRepository outboxRepository, MailAsyncExecutor mailAsyncExecutor) {
        this.outboxRepository = outboxRepository;
        this.mailAsyncExecutor = mailAsyncExecutor;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<EmailOutbox> pending = outboxRepository.findTop20ByStatusOrderByCreatedAtAsc("PENDING");
        if (pending.isEmpty()) {
            return;
        }
        log.info("[quartz] retry {} pending emails", pending.size());
        pending.forEach(row -> mailAsyncExecutor.dispatch(row.getId()));
    }
}
