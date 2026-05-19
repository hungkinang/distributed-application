package edu.bookingtour.svc.payment.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "booking-service")
public class BookingServiceProperties { private String baseUrl = "http://localhost:8080"; }
