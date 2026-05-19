package edu.bookingtour.svc.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tour-service")
public class TourServiceProperties {
    /** Base URL svc-tour (Docker: http://svc-tour:8080). */
    private String baseUrl = "http://localhost:8080";
}
