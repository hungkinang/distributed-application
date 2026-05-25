package edu.bookingtour.svc.email.service;

import edu.bookingtour.svc.email.domain.EmailOutbox;
import edu.bookingtour.svc.email.repo.EmailOutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class MailDispatchService {

    private static final Logger log = LoggerFactory.getLogger(MailDispatchService.class);

    private final JavaMailSender mailSender;
    private final EmailOutboxRepository outboxRepository;

    @Value("${email.from}")
    private String fromAddress;

    @Value("${email.mock-send:true}")
    private boolean mockSend;

    public MailDispatchService(JavaMailSender mailSender, EmailOutboxRepository outboxRepository) {
        this.mailSender = mailSender;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public UUID enqueueBookingConfirmation(UUID bookingUuid, String recipient, String subject, String body) {
        EmailOutbox row = new EmailOutbox();
        row.setId(UUID.randomUUID());
        row.setBookingUuid(bookingUuid);
        row.setRecipient(recipient);
        row.setSubject(subject);
        row.setBody(body);
        outboxRepository.save(row);
        return row.getId();
    }

    public void processOutbox(UUID outboxId) {
        log.info("[mail-async] thread={} processing outboxId={}", Thread.currentThread().getName(), outboxId);
        outboxRepository.findById(outboxId).ifPresent(this::sendNow);
    }

    @Transactional
    public void sendNow(EmailOutbox row) {
        if ("SENT".equals(row.getStatus())) {
            return;
        }
        if (mockSend) {
            log.info("[email-mock] to={} subject={} body={}", row.getRecipient(), row.getSubject(), row.getBody());
        } else {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromAddress);
            msg.setTo(row.getRecipient());
            msg.setSubject(row.getSubject());
            msg.setText(row.getBody());
            mailSender.send(msg);
        }
        row.setStatus("SENT");
        row.setSentAt(Instant.now());
    }
}
