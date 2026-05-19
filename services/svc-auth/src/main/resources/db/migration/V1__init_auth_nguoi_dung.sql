CREATE TABLE IF NOT EXISTS nguoi_dung (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    ten_dang_nhap   VARCHAR(255) DEFAULT NULL,
    email           VARCHAR(255) DEFAULT NULL,
    mat_khau        VARCHAR(255) DEFAULT NULL,
    vai_tro         TINYTEXT,
    ngay_tao        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ho_ten          VARCHAR(255) DEFAULT NULL,
    `number`        VARCHAR(255) DEFAULT NULL,
    provider        VARCHAR(255) DEFAULT NULL,
    anh_dai_dien    VARCHAR(255) DEFAULT NULL,
    UNIQUE KEY uk_nguoi_dung_username (ten_dang_nhap),
    UNIQUE KEY uk_nguoi_dung_email (email)
);
