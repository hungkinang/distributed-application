CREATE TABLE IF NOT EXISTS yeu_thich (
 id INT NOT NULL AUTO_INCREMENT,
 id_nguoi_dung INT NOT NULL,
 id_chuyen_di INT NOT NULL,
 ngay_them TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (id),
 UNIQUE KEY uk_fav (id_nguoi_dung, id_chuyen_di),
 KEY idx_fav_tour (id_chuyen_di)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
