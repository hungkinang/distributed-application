CREATE TABLE IF NOT EXISTS news_fetch_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    query_text VARCHAR(512),
    status_code INT,
    article_count INT,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
