package edu.bookingtour.svc.booking.api;

import edu.bookingtour.svc.booking.api.dto.InternalPaymentPayload;
import edu.bookingtour.svc.booking.service.ReservationApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings/internal")
public class InternalBookingController {

    private final ReservationApplicationService reservations;
    private final String expectedToken;

    public InternalBookingController(
            ReservationApplicationService reservations,
            @Value("${internal.service-token:}") String expectedToken) {
        this.reservations = reservations;
        this.expectedToken = expectedToken;
    }

    /**
     * Gọi từ svc-payment sau VNPay callback (mạng nội bộ Docker). Bảo vệ bằng header
     * X-Internal-Token.
     */
    @PostMapping("/payment-result")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void paymentResult(
            @RequestHeader("X-Internal-Token") String token, @Valid @RequestBody InternalPaymentPayload body) {
        reservations.applyInternalPayment(body.reservationId(), body.status(), token, expectedToken);
    }
}
