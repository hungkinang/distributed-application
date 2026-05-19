CREATE TABLE IF NOT EXISTS flight_lookup_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    origin VARCHAR(16),
    destination VARCHAR(16),
    depart_date DATE,
    price DOUBLE,
    raw_json MEDIUMTEXT,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_route (origin, destination, depart_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
