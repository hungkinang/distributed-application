package edu.bookingtour.svc.flight.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class AmadeusClient {

    @Value("${amadeus.api.key}")
    private String apiKey;

    @Value("${amadeus.api.secret}")
    private String apiSecret;

    @Value("${amadeus.api.base-url}")
    private String baseUrl;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient http = HttpClient.newHttpClient();
    private String accessToken;
    private Instant tokenExpiry;

    private synchronized String getAccessToken() throws Exception {
        if (accessToken != null && tokenExpiry != null && Instant.now().isBefore(tokenExpiry)) {
            return accessToken;
        }
        String body = "grant_type=client_credentials"
                + "&client_id=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(apiSecret, StandardCharsets.UTF_8);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/v1/security/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Amadeus OAuth2: " + resp.body());
        }
        JsonNode json = mapper.readTree(resp.body());
        accessToken = json.get("access_token").asText();
        int expiresIn = json.get("expires_in").asInt();
        tokenExpiry = Instant.now().plusSeconds(expiresIn - 60L);
        return accessToken;
    }

    public Map<String, Object> getCheapestFlight(String origin, String destination, LocalDate departureDate) {
        Map<String, Object> result = new HashMap<>();
        String date = departureDate.toString();
        try {
            if (apiKey == null || apiKey.isBlank() || apiSecret == null || apiSecret.isBlank()) {
                result.put("price", 0.0);
                result.put("note", "thiếu AMADEUS_API_KEY / AMADEUS_API_SECRET");
                return result;
            }
            String token = getAccessToken();
            String url = String.format(
                    "%s/v2/shopping/flight-offers?originLocationCode=%s&destinationLocationCode=%s&departureDate=%s&adults=1&currencyCode=VND&max=1",
                    baseUrl, origin, destination, date);
            HttpRequest request =
                    HttpRequest.newBuilder().uri(URI.create(url)).header("Authorization", "Bearer " + token).GET().build();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                result.put("error", response.body());
                return result;
            }
            JsonNode json = mapper.readTree(response.body());
            JsonNode data = json.get("data");
            if (data != null && data.isArray() && !data.isEmpty()) {
                JsonNode offer = data.get(0);
                double price = offer.get("price").get("grandTotal").asDouble();
                result.put("price", price);
                JsonNode segments = offer.get("itineraries").get(0).get("segments");
                if (segments != null && segments.size() > 0) {
                    JsonNode seg = segments.get(0);
                    result.put("airline", seg.get("carrierCode").asText());
                    result.put("flightNumber", seg.get("carrierCode").asText() + seg.get("number").asText());
                    result.put("departureTime", seg.get("departure").get("at").asText());
                    result.put("arrivalTime", seg.get("arrival").get("at").asText());
                }
            }
        } catch (Exception e) {
            result.put("error", e.getMessage());
        }
        return result;
    }

    public double getCheapestPrice(String origin, String destination, String departureDate) {
        Map<String, Object> m = getCheapestFlight(origin, destination, LocalDate.parse(departureDate));
        if (m.containsKey("price")) {
            return (double) m.get("price");
        }
        return 0;
    }
}
