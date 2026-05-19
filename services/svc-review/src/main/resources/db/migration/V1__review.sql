CREATE TABLE IF NOT EXISTS danh_gia (
  id INT NOT NULL AUTO_INCREMENT,
  id_chuyen_di INT NOT NULL,
  id_nguoi_dung INT NOT NULL,
  diem INT DEFAULT NULL,
  binh_luan VARCHAR(512) DEFAULT NULL,
  ngay_danh_gia TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_dg_tour (id_chuyen_di),
  KEY idx_dg_user (id_nguoi_dung),
  CONSTRAINT chk_diem CHECK ((diem between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
