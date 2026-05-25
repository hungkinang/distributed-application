package edu.bookingtour.svc.notification;

import edu.bookingtour.commons.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ServiceInfoController {

    @GetMapping("/api/notifications/info")
    public ApiResponse<Map<String, String>> info() {
        return ApiResponse.ok(Map.of("service", "svc-notification", "role", "RabbitMQ consumer — in-app notifications"));
    }
}
