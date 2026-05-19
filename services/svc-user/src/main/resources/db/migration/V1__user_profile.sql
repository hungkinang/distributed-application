CREATE TABLE IF NOT EXISTS user_profile (
    user_id         INT NOT NULL PRIMARY KEY COMMENT 'Trùng subject JWT / id auth_db',
    ho_ten          VARCHAR(255) DEFAULT NULL,
    email           VARCHAR(255) DEFAULT NULL,
    `number`        VARCHAR(255) DEFAULT NULL,
    anh_dai_dien    VARCHAR(255) DEFAULT NULL,
    UNIQUE KEY uk_user_profile_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
