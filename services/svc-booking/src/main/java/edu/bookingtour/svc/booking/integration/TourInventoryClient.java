package edu.bookingtour.svc.booking.integration;

import edu.bookingtour.commons.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class TourInventoryClient {

    private static final Logger log = LoggerFactory.getLogger(TourInventoryClient.class);

    private final RestClient restClient;

    @Value("${internal.service-token:}")
    private String serviceToken;

    public TourInventoryClient(@Value("${tour.service-base-url:http://svc-tour:8080}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @CircuitBreaker(name = "tourInventory", fallbackMethod = "reserveFallback")
    @Retry(name = "tourInventory")
    public void reserveSeats(int chuyenDiId, int soLuong) {
        log.debug("REST reserve seats tour={} qty={}", chuyenDiId, soLuong);
        restClient
                .post()
                .uri("/api/tours/internal/seats/reserve")
                .header("X-Service-Token", serviceToken)
                .body(Map.of("chuyenDiId", chuyenDiId, "soLuong", soLuong))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new BusinessException("TOUR_SERVICE_ERROR", "Tour service từ chối giữ chỗ: " + res.getStatusCode());
                })
                .toBodilessEntity();
    }

    @SuppressWarnings("unused")
    private void reserveFallback(int chuyenDiId, int soLuong, Throwable cause) {
        log.warn("Circuit breaker open for tour inventory: {}", cause.getMessage());
        throw new BusinessException(
                "TOUR_SERVICE_UNAVAILABLE",
                "Dịch vụ tour tạm thời không khả dụng, vui lòng thử lại sau");
    }
}
