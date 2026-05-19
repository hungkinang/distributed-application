package edu.bookingtour.svc.flight.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bookingtour.svc.flight.domain.FlightLookupLog;
import edu.bookingtour.svc.flight.integration.AmadeusClient;
import edu.bookingtour.svc.flight.repo.FlightLookupLogRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/flights", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightRestController {

    private final AmadeusClient amadeus;
    private final FlightLookupLogRepository logs;
    private final ObjectMapper json = new ObjectMapper();

    public FlightRestController(AmadeusClient amadeus, FlightLookupLogRepository logs) {
        this.amadeus = amadeus;
        this.logs = logs;
    }

    @GetMapping("/price")
    public Map<String, Object> price(
            @RequestParam String from, @RequestParam String to, @RequestParam String date) {
        LocalDate depart = LocalDate.parse(date);
        Map<String, Object> detail = amadeus.getCheapestFlight(from.toUpperCase(), to.toUpperCase(), depart);

        FlightLookupLog row = new FlightLookupLog();
        row.setOrigin(from.toUpperCase());
        row.setDestination(to.toUpperCase());
        row.setDepartDate(depart);
        if (detail.get("price") instanceof Double pd) {
            row.setPrice(pd);
        }
        try {
            row.setRawJson(json.writeValueAsString(detail));
        } catch (Exception ignored) {
        }
        logs.save(row);

        return Map.of(
                "price", detail.getOrDefault("price", 0.0),
                "detail", detail);
    }
}
