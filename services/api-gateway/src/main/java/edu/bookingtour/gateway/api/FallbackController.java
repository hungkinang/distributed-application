package edu.bookingtour.gateway.api;

import edu.bookingtour.commons.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping
    public ApiResponse<Void> fallback() {
        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE, "Dịch vụ tạm thời quá tải — circuit breaker đang mở");
    }
}
