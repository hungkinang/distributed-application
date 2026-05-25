package edu.bookingtour.svc.email.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MailAsyncExecutor {

    private final MailDispatchService mailDispatchService;

    public MailAsyncExecutor(MailDispatchService mailDispatchService) {
        this.mailDispatchService = mailDispatchService;
    }

    @Async("mailTaskExecutor")
    public void dispatch(UUID outboxId) {
        mailDispatchService.processOutbox(outboxId);
    }
}
