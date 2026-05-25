CREATE TABLE user_notification (
    id BINARY(16) NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    booking_uuid BINARY(16) NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    read_flag TINYINT(1) NOT NULL DEFAULT 0,
    created_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    INDEX idx_notification_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
