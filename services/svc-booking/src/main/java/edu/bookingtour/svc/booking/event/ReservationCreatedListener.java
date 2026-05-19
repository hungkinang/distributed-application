package edu.bookingtour.svc.booking.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(ReservationCreatedListener.class);

    @EventListener
    public void onCreated(ReservationCreatedEvent e) {
        log.info(
                "[booking.event] ReservationCreated bookingId={} userId={} chuyenDiId={} tongGia={} at={}",
                e.bookingId(),
                e.userId(),
                e.chuyenDiId(),
                e.tongGia(),
                e.at());

        /** Nơi phát Rabbit/Kafka trong tương lai, ví dụ xác nhận chỗ, thanh toán. */
    }
}
