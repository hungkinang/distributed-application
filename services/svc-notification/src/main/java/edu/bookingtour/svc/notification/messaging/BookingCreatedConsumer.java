package edu.bookingtour.svc.notification.messaging;

import edu.bookingtour.commons.messaging.BookingCreatedMessage;
import edu.bookingtour.commons.messaging.RabbitMQConstants;
import edu.bookingtour.svc.notification.domain.UserNotification;
import edu.bookingtour.svc.notification.repo.UserNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class BookingCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(BookingCreatedConsumer.class);

    private final UserNotificationRepository repository;

    public BookingCreatedConsumer(UserNotificationRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_NOTIFICATION_BOOKING)
    @Transactional
    public void onBookingCreated(BookingCreatedMessage message) {
        log.info("[rabbitmq] notification booking.created userId={}", message.userId());
        UserNotification n = new UserNotification();
        n.setId(UUID.randomUUID());
        n.setUserId(message.userId());
        n.setBookingUuid(message.bookingUuid());
        n.setTitle("Đặt tour thành công");
        n.setMessage("Đơn #" + message.bookingId() + " đang chờ thanh toán.");
        n.setReadFlag(false);
        repository.save(n);
    }
}
