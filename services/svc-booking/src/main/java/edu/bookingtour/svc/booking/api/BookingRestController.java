package edu.bookingtour.svc.booking.api;

import edu.bookingtour.svc.booking.api.dto.CreateReservationRequest;
import edu.bookingtour.svc.booking.api.dto.ReservationResponse;
import edu.bookingtour.svc.booking.service.ReservationApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {

    private final ReservationApplicationService reservations;

    public BookingRestController(ReservationApplicationService reservations) {
        this.reservations = reservations;
    }

    @PostMapping("/reservations")
    public ReservationResponse create(
            @Valid @RequestBody CreateReservationRequest body, @AuthenticationPrincipal Jwt jwt) {
        int uid = Integer.parseInt(jwt.getSubject());
        return reservations.create(body, uid);
    }

    @GetMapping("/reservations/me")
    public List<ReservationResponse> mine(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int uid = Integer.parseInt(jwt.getSubject());
        return reservations.findMine(uid);
    }

    @GetMapping("/reservations/{id}")
    public ReservationResponse one(@AuthenticationPrincipal Jwt jwt, @PathVariable int id) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        int uid = Integer.parseInt(jwt.getSubject());
        return reservations.findByIdForUser(id, uid);
    }
}
