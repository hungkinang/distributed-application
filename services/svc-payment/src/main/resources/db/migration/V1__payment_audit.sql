CREATE TABLE IF NOT EXISTS vnp_callback_log (
  id BIGINT NOT NULL AUTO_INCREMENT,
  txn_ref VARCHAR(64) NOT NULL,
  response_code VARCHAR(16),
  verified TINYINT(1) NOT NULL DEFAULT 0,
  payload_json MEDIUMTEXT,
  created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_txn (txn_ref)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
