package edu.bookingtour.svc.email;

import edu.bookingtour.commons.api.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ServiceInfoController {

    @GetMapping("/api/email/info")
    public ApiResponse<Map<String, String>> info() {
        return ApiResponse.ok(Map.of(
                "service", "svc-email",
                "role", "RabbitMQ consumer + @Async mail + Quartz retry"));
    }
}
