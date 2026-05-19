CREATE TABLE IF NOT EXISTS dat_cho (
    id              INT NOT NULL AUTO_INCREMENT,
    id_nguoi_dung   INT DEFAULT NULL COMMENT 'Tham chiếu logic tới svc-auth/user',
    id_chuyen_di    INT NOT NULL COMMENT 'Tham chiếu logic tới tour (monolith hoặc svc-tour)',
    so_luong        INT DEFAULT NULL,
    ngay_dat        DATE DEFAULT NULL,
    trang_thai      VARCHAR(50) DEFAULT NULL,
    id_ma_giam_gia  INT DEFAULT NULL COMMENT 'Coupon — tách payment sau',
    dia_chi         VARCHAR(500) DEFAULT NULL,
    email           VARCHAR(255) DEFAULT NULL,
    ghi_chu         TEXT,
    ho_ten          VARCHAR(255) DEFAULT NULL,
    so_dien_thoai   VARCHAR(20) DEFAULT NULL,
    created_at      DATETIME(6) DEFAULT NULL,
    tong_gia        DOUBLE DEFAULT NULL,
    PRIMARY KEY (id),
    KEY idx_dat_cho_user (id_nguoi_dung),
    KEY idx_dat_cho_tour (id_chuyen_di)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS cho_xac_nhan (
    id              INT NOT NULL AUTO_INCREMENT,
    id_dat_cho      INT DEFAULT NULL,
    trang_thai      TINYTEXT,
    ngay_cap_nhat   TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_cxn_dat_cho (id_dat_cho),
    CONSTRAINT fk_cxn_dat_cho FOREIGN KEY (id_dat_cho) REFERENCES dat_cho (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
