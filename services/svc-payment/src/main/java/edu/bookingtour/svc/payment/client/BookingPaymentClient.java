package edu.bookingtour.svc.payment.client;
import edu.bookingtour.svc.payment.config.BookingServiceProperties;
import edu.bookingtour.svc.payment.config.InternalSecretProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
@Component
public class BookingPaymentClient {
    private final RestTemplate rt;
    private final BookingServiceProperties booking;
    private final InternalSecretProperties internal;

    public BookingPaymentClient(RestTemplateBuilder b, BookingServiceProperties booking, InternalSecretProperties internal) {
        this.rt = b.build();
        this.booking = booking;
        this.internal = internal;
    }

    public void notifyBookingPaid(long reservationId, String status) {
        String url = booking.getBaseUrl().replaceAll("/$", "") + "/api/bookings/internal/payment-result";
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        h.set("X-Internal-Token", internal.getServiceToken());
        var body = Map.of("reservationId", reservationId, "status", status);
        rt.postForEntity(url, new HttpEntity<>(body, h), Void.class);
    }
}
