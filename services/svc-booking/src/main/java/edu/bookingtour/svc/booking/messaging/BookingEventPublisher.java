package edu.bookingtour.svc.booking.messaging;

import edu.bookingtour.commons.messaging.BookingCreatedMessage;
import edu.bookingtour.commons.messaging.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(BookingEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public BookingEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishBookingCreated(BookingCreatedMessage message) {
        log.info("[rabbitmq] publish booking.created uuid={}", message.bookingUuid());
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.BOOKING_EXCHANGE,
                RabbitMQConstants.ROUTING_BOOKING_CREATED,
                message);
    }
}
