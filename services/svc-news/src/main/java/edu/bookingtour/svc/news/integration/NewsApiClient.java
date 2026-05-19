package edu.bookingtour.svc.news.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bookingtour.svc.news.dto.ArticleDTO;
import edu.bookingtour.svc.news.dto.NewsResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
public class NewsApiClient {

    private static final String API_URL = "https://newsapi.org/v2/everything";

    @Value("${news.api.token}")
    private String token;

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    public NewsFetchResult getLatestMeta() {
        try {
            return getNewsWithMeta("travel OR tourism OR destination");
        } catch (Exception e) {
            return new NewsFetchResult(500, Collections.emptyList());
        }
    }

    public List<ArticleDTO> getLatest() {
        return getLatestMeta().articles();
    }

    /** @return đôi giá trị (httpStatus, articles) để audit */
    public NewsFetchResult getNewsWithMeta(String query) throws Exception {
        if (token == null || token.isBlank()) {
            return new NewsFetchResult(401, Collections.emptyList());
        }
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = API_URL
                + "?q="
                + encodedQuery
                + "&language=en&sortBy=publishedAt&pageSize=12&apiKey="
                + token;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            NewsResponseDTO dto = mapper.readValue(response.body(), NewsResponseDTO.class);
            List<ArticleDTO> arts = dto.getArticles() != null ? dto.getArticles() : Collections.emptyList();
            return new NewsFetchResult(200, arts);
        }
        return new NewsFetchResult(response.statusCode(), Collections.emptyList());
    }

    public List<ArticleDTO> getNews(String query) throws Exception {
        return getNewsWithMeta(query).articles();
    }

    public record NewsFetchResult(int statusCode, List<ArticleDTO> articles) {}
}
