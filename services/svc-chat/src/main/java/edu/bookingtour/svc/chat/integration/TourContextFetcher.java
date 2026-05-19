package edu.bookingtour.svc.chat.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bookingtour.svc.chat.config.TourServiceProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class TourContextFetcher {

    private final RestTemplate rt = new RestTemplate();
    private final TourServiceProperties tour;
    private final ObjectMapper mapper = new ObjectMapper();

    public TourContextFetcher(TourServiceProperties tour) {
        this.tour = tour;
    }

    /** Gọi GET /api/tours/chat-context trên svc-tour. */
    public List<Map<String, Object>> loadTours(int limit) {
        try {
            String base = tour.getBaseUrl().replaceAll("/$", "");
            String body = rt.getForObject(base + "/api/tours/chat-context?limit={l}", String.class, limit);
            if (body == null) {
                return Collections.emptyList();
            }
            return mapper.readValue(body, new TypeReference<>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
