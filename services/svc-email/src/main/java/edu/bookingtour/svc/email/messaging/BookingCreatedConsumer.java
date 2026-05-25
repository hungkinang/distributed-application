package edu.bookingtour.svc.email.messaging;

import edu.bookingtour.commons.messaging.BookingCreatedMessage;
import edu.bookingtour.commons.messaging.RabbitMQConstants;
import edu.bookingtour.svc.email.service.MailAsyncExecutor;
import edu.bookingtour.svc.email.service.MailDispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(BookingCreatedConsumer.class);

    private final MailDispatchService mailDispatchService;
    private final MailAsyncExecutor mailAsyncExecutor;

    public BookingCreatedConsumer(MailDispatchService mailDispatchService, MailAsyncExecutor mailAsyncExecutor) {
        this.mailDispatchService = mailDispatchService;
        this.mailAsyncExecutor = mailAsyncExecutor;
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_EMAIL_BOOKING)
    public void onBookingCreated(BookingCreatedMessage message) {
        log.info("[rabbitmq] consume booking.created bookingUuid={} email={}", message.bookingUuid(), message.email());
        String subject = "Xác nhận đặt tour #" + message.bookingId();
        String body = """
                Xin chào %s,

                Đơn đặt tour của bạn đã được ghi nhận (mã: %s).
                Số lượng: %d | Tổng tiền: %s VND

                Trạng thái: chờ thanh toán.

                — BookingTour
                """.formatted(
                message.hoTen(),
                message.bookingUuid(),
                message.soLuong(),
                message.tongGia());

        UUID outboxId = mailDispatchService.enqueueBookingConfirmation(
                message.bookingUuid(), message.email(), subject, body);
        mailAsyncExecutor.dispatch(outboxId);
    }
}
