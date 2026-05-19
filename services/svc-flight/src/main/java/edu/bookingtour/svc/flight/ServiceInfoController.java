package edu.bookingtour.svc.flight;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${booking.api-base-path}")
public class ServiceInfoController {

    @GetMapping("/info")
    public Map<String, String> info(@Value("${spring.application.name}") String name) {
        return Map.of("service", name, "status", "Amadeus cheapest + flight_lookup_log");
    }
}
