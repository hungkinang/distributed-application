package edu.bookingtour.svc.news.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "news_fetch_log")
public class NewsFetchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "query_text")
    private String queryText;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "article_count")
    private Integer articleCount;

    @Column(name = "created_at")
    private Instant createdAt;

    @jakarta.persistence.PrePersist
    void pre() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
