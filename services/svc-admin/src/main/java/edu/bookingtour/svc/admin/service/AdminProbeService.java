package edu.bookingtour.svc.admin.service;

import edu.bookingtour.svc.admin.config.AdminBackendProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AdminProbeService {

    private static final Map<String, String> INFO_PATHS =
            Map.ofEntries(
                    Map.entry("auth", "/api/auth/info"),
                    Map.entry("user", "/api/users/info"),
                    Map.entry("tour", "/api/tours/info"),
                    Map.entry("booking", "/api/bookings/info"),
                    Map.entry("payment", "/api/payments/info"),
                    Map.entry("contact", "/api/contact/info"),
                    Map.entry("review", "/api/reviews/info"),
                    Map.entry("favorite", "/api/favorites/info"),
                    Map.entry("flight", "/api/flights/info"),
                    Map.entry("news", "/api/news/info"),
                    Map.entry("chat", "/api/chat/info"));

    private final AdminBackendProperties props;
    private final RestTemplate rt = new RestTemplate();

    public AdminProbeService(AdminBackendProperties props) {
        this.props = props;
    }

    /** Gọi từng /info để có bức tranh stack; không fail nếu một service die. */
    public Map<String, Object> probeAll() {
        Map<String, Object> out = new LinkedHashMap<>();
        for (var e : INFO_PATHS.entrySet()) {
            String name = e.getKey();
            String path = e.getValue();
            String base = props.getBackends().get(name);
            if (base == null || base.isBlank()) {
                out.put(name, Map.of("reachable", false, "error", "missing admin.backends." + name));
                continue;
            }
            String url = base.replaceAll("/$", "") + path;
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> body = rt.getForObject(url, Map.class);
                out.put(name, Map.of("reachable", true, "body", body != null ? body : Map.of()));
            } catch (RestClientException ex) {
                out.put(name, Map.of("reachable", false, "error", ex.getMessage()));
            }
        }
        return Map.of("services", out, "count", out.size());
    }
}
