package edu.bookingtour.svc.booking.event;

import edu.bookingtour.commons.messaging.BookingCreatedMessage;
import edu.bookingtour.svc.booking.messaging.BookingEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReservationCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(ReservationCreatedListener.class);

    private final BookingEventPublisher publisher;

    public ReservationCreatedListener(BookingEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void onCreated(ReservationCreatedEvent e) {
        log.info(
                "[booking.event] ReservationCreated uuid={} bookingId={} userId={}",
                e.bookingUuid(),
                e.bookingId(),
                e.userId());

        publisher.publishBookingCreated(new BookingCreatedMessage(
                e.bookingUuid(),
                e.bookingId(),
                e.userId(),
                e.chuyenDiId(),
                e.email(),
                e.hoTen(),
                e.soLuong(),
                e.tongGia() != null ? BigDecimal.valueOf(e.tongGia()) : BigDecimal.ZERO,
                e.at()));
    }
}
