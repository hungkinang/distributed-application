package edu.bookingtour.svc.news.api;

import edu.bookingtour.svc.news.domain.NewsFetchLog;
import edu.bookingtour.svc.news.dto.ArticleDTO;
import edu.bookingtour.svc.news.integration.NewsApiClient;
import edu.bookingtour.svc.news.repo.NewsFetchLogRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsRestController {

    private final NewsApiClient client;
    private final NewsFetchLogRepository logs;

    public NewsRestController(NewsApiClient client, NewsFetchLogRepository logs) {
        this.client = client;
        this.logs = logs;
    }

    @GetMapping("/articles/latest")
    public List<ArticleDTO> latest() {
        var meta = client.getLatestMeta();
        saveLog("travel OR tourism OR destination", meta.statusCode(), meta.articles().size());
        return meta.articles();
    }

    @GetMapping("/articles")
    public List<ArticleDTO> byQuery(@RequestParam(defaultValue = "travel") String q) throws Exception {
        var meta = client.getNewsWithMeta(q);
        saveLog(q, meta.statusCode(), meta.articles().size());
        return meta.articles();
    }

    private void saveLog(String q, int code, int n) {
        NewsFetchLog row = new NewsFetchLog();
        row.setQueryText(q);
        row.setStatusCode(code);
        row.setArticleCount(n);
        logs.save(row);
    }
}
