CREATE TABLE email_outbox (
    id BINARY(16) NOT NULL PRIMARY KEY,
    booking_uuid BINARY(16) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    subject VARCHAR(500) NOT NULL,
    body TEXT NOT NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    sent_at TIMESTAMP(3) NULL,
    INDEX idx_email_outbox_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
