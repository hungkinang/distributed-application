package edu.bookingtour.svc.tour.api;

import edu.bookingtour.commons.api.ApiResponse;
import edu.bookingtour.svc.tour.service.SeatInventoryService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tours/internal")
public class InternalTourController {

    private final SeatInventoryService seatInventoryService;

    @Value("${internal.service-token:}")
    private String expectedToken;

    public InternalTourController(SeatInventoryService seatInventoryService) {
        this.seatInventoryService = seatInventoryService;
    }

    public record ReserveSeatsRequest(
            @NotNull Integer chuyenDiId,
            @Min(1) Integer soLuong) {}

    @PostMapping("/seats/reserve")
    public ApiResponse<Void> reserve(
            @RequestHeader(value = "X-Service-Token", required = false) String token,
            @RequestBody ReserveSeatsRequest request) {
        assertServiceToken(token);
        seatInventoryService.reserveSeats(request.chuyenDiId(), request.soLuong());
        return ApiResponse.ok("Đã giữ chỗ", null);
    }

    private void assertServiceToken(String token) {
        if (expectedToken == null || expectedToken.isBlank() || !expectedToken.equals(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "invalid service token");
        }
    }
}
