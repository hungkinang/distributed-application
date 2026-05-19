SET FOREIGN_KEY_CHECKS=0;
SET NAMES utf8mb4;

CREATE TABLE `diem_den` (
  `id` int NOT NULL AUTO_INCREMENT,
  `thanh_pho` varchar(255) DEFAULT NULL,
  `quoc_gia` varchar(255) DEFAULT NULL,
  `chau_luc` varchar(255) DEFAULT NULL,
  `hinh_anh` varchar(255) DEFAULT NULL,
  `noi_bat` tinyint(1) DEFAULT '0',
  `mo_ta` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Dumping data for table `diem_den`
--

LOCK TABLES `diem_den` WRITE;
/*!40000 ALTER TABLE `diem_den` DISABLE KEYS */;
INSERT INTO `diem_den` VALUES (1,'Hà Nội','Việt Nam','Á Đông','/anh/diemden/hanoi.jpg',1,NULL),(2,'Đà Nẵng','Việt Nam','Á Đông','/anh/diemden/danang.jpg',1,NULL),(3,'Huế','Việt Nam','Á Đông','/anh/diemden/hue.jpg',0,NULL),(4,'Hạ Long','Việt Nam','Á Đông','/anh/diemden/halong.jpg',1,NULL),(5,'Nha Trang','Việt Nam','Á Đông','/anh/diemden/nhatrang.jpg',0,NULL),(6,'Phú Quốc','Việt Nam','Á Đông','/anh/diemden/phuquoc.jpg',0,NULL),(7,'Sa Pa','Việt Nam','Á Đông','/anh/diemden/sapa.jpg',1,NULL),(8,'Cần Thơ','Việt Nam','Á Đông','/anh/diemden/cantho.jpg',0,NULL),(9,'Bắc Kinh','Trung Quốc','Á Đông','/anh/diemden/backinh.jpg',0,NULL),(10,'Thượng Hải','Trung Quốc','Á Đông','/anh/diemden/thuonghai.jpg',1,NULL),(11,'Trương Gia Giới','Trung Quốc','Á Đông','/anh/diemden/truonggiagioi.jpg',0,NULL),(12,'Tokyo','Nhật Bản','Á Đông','/anh/diemden/tokyo.jpg',1,NULL),(13,'Kyoto','Nhật Bản','Á Đông','/anh/diemden/kyoto.jpg',0,NULL),(14,'Osaka','Nhật Bản','Á Đông','/anh/diemden/osaka.jpg',0,NULL),(15,'Seoul','Hàn Quốc','Á Đông','/anh/diemden/seoul.jpg',0,NULL);
/*!40000 ALTER TABLE `diem_den` ENABLE KEYS */;
UNLOCK TABLES;
CREATE TABLE `diem_don` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Dumping data for table `diem_don`
--

LOCK TABLES `diem_don` WRITE;
/*!40000 ALTER TABLE `diem_don` DISABLE KEYS */;
INSERT INTO `diem_don` VALUES (1,'Hà Nội'),(2,'Hồ Chí Minh'),(3,'Đà Nẵng');
/*!40000 ALTER TABLE `diem_don` ENABLE KEYS */;
UNLOCK TABLES;
CREATE TABLE `phuong_tien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `loai` tinytext,
  `hang` varchar(255) DEFAULT NULL,
  `id_diem_den` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_diem_den` (`id_diem_den`),
  CONSTRAINT `phuong_tien_ibfk_1` FOREIGN KEY (`id_diem_den`) REFERENCES `diem_den` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Dumping data for table `phuong_tien`
--

LOCK TABLES `phuong_tien` WRITE;
/*!40000 ALTER TABLE `phuong_tien` DISABLE KEYS */;
INSERT INTO `phuong_tien` VALUES (1,'Plane','Vietnam Airlines',1),(2,'Plane','Vietjet',2),(3,'Bus','SinhCafe Bus',2),(4,'Plane','Vietjet',4),(5,'Plane','China Eastern',9),(6,'Plane','ANA',12),(7,'Bus','Local Bus',7),(8,'Plane','Korean Air',15),(9,'Plane','Ferry Phu Quoc',6);
/*!40000 ALTER TABLE `phuong_tien` ENABLE KEYS */;
UNLOCK TABLES;
CREATE TABLE `noi_luu_tru` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) DEFAULT NULL,
  `loai` varchar(100) DEFAULT NULL,
  `dia_chi` varchar(255) DEFAULT NULL,
  `gia` decimal(10,2) DEFAULT NULL,
  `id_diem_den` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_diem_den` (`id_diem_den`),
  CONSTRAINT `noi_luu_tru_ibfk_1` FOREIGN KEY (`id_diem_den`) REFERENCES `diem_den` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Dumping data for table `noi_luu_tru`
--

LOCK TABLES `noi_luu_tru` WRITE;
/*!40000 ALTER TABLE `noi_luu_tru` DISABLE KEYS */;
INSERT INTO `noi_luu_tru` VALUES (1,'Khách sạn Hà Nội 1','Hotel','Hoàn Kiếm, Hà Nội',1200000.00,1),(2,'Nhà nghỉ Hà Nội giá rẻ','Homestay','Tây Hồ, Hà Nội',350000.00,1),(3,'Sunrise Đà Nẵng Hotel','Hotel','Mỹ Khê, Đà Nẵng',900000.00,2),(4,'Apartment Đà Nẵng Center','Apartment','Hải Châu, Đà Nẵng',650000.00,2),(5,'Khách sạn Huế River','Hotel','Huế',700000.00,3),(6,'Resort Hạ Long Bay','Resort','Hạ Long',1500000.00,4),(7,'Hotel Nha Trang Beach','Hotel','Nha Trang',1100000.00,5),(8,'Resort Phú Quốc 5*','Resort','Phú Quốc',2200000.00,6),(9,'Homestay Sa Pa View','Homestay','Sa Pa',400000.00,7),(10,'Khách sạn Cần Thơ Riverside','Hotel','Cần Thơ',550000.00,8),(11,'Beijing Grand Hotel','Hotel','Bắc Kinh',1300.00,9),(12,'Shanghai River Hotel','Hotel','Thượng Hải',1400.00,10),(13,'Zhangjiajie Inn','Inn','Trương Gia Giới',500.00,11),(14,'Tokyo Central Hotel','Hotel','Tokyo',15000.00,12),(15,'Seoul Cozy Stay','Hotel','Seoul',1200000.00,15);
/*!40000 ALTER TABLE `noi_luu_tru` ENABLE KEYS */;
UNLOCK TABLES;
CREATE TABLE `chuyen_di` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tieu_de` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `gia` decimal(10,2) DEFAULT NULL,
  `ngay_khoi_hanh` date DEFAULT NULL,
  `ngay_ket_thuc` date DEFAULT NULL,
  `id_diem_den` int DEFAULT NULL,
  `id_phuong_tien` int DEFAULT NULL,
  `id_noi_luu_tru` int DEFAULT NULL,
  `noi_bat` tinyint(1) DEFAULT '0',
  `hinh_anh` varchar(255) DEFAULT NULL,
  `highlight` text,
  `id_diem_don` int DEFAULT NULL,
  `trang_thai` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_diem_den` (`id_diem_den`),
  KEY `id_phuong_tien` (`id_phuong_tien`),
  KEY `id_noi_luu_tru` (`id_noi_luu_tru`),
  KEY `FKf7y4cdxg2tmd2m7p0s4jwspxo` (`id_diem_don`),
  CONSTRAINT `chuyen_di_ibfk_1` FOREIGN KEY (`id_diem_den`) REFERENCES `diem_den` (`id`),
  CONSTRAINT `chuyen_di_ibfk_2` FOREIGN KEY (`id_phuong_tien`) REFERENCES `phuong_tien` (`id`),
  CONSTRAINT `chuyen_di_ibfk_3` FOREIGN KEY (`id_noi_luu_tru`) REFERENCES `noi_luu_tru` (`id`),
  CONSTRAINT `FKf7y4cdxg2tmd2m7p0s4jwspxo` FOREIGN KEY (`id_diem_don`) REFERENCES `diem_don` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Dumping data for table `chuyen_di`
--

LOCK TABLES `chuyen_di` WRITE;
/*!40000 ALTER TABLE `chuyen_di` DISABLE KEYS */;
INSERT INTO `chuyen_di` VALUES (1,'Tour Hà Nội 3N2Đ','Tham quan Hồ Gươm, Văn Miếu, Lăng Bác',2500000.00,'2026-03-18','2026-03-25',1,3,1,1,'/anh/chuyendi/hanoi.jpg','Khám phá chiều sâu văn hóa của \"Thủ đô ngàn năm văn hiến\". Quý khách sẽ có những phút giây lắng đọng tại Lăng Bác, tìm hiểu lịch sử tại Văn Miếu - Quốc Tử Giám. Đặc biệt, trải nghiệm Xích lô dạo quanh 36 phố phường vào buổi xế chiều và thưởng thức ẩm thực tinh tế như Phở Thìn, Chả cá Lã Vọng sẽ là ký ức khó quên.',1,NULL),(2,'Tour 4N3Đ Đà Nẵng - Hội An','Bà Nà Hill, Hội An cổ',4200000.00,'2026-03-17','2026-03-20',2,1,3,0,'/anh/diemden/danang.jpg','Hành trình xuyên qua những vùng đất di sản. Chạm tay vào \"mây ngàn\" tại Bà Nà Hills với cây Cầu Vàng biểu tượng. Khi màn đêm buông xuống, quý khách sẽ được thả mình vào không gian lung linh của Phố cổ Hội An, tự tay thả hoa đăng trên sông Hoài và thưởng thức show diễn \"Ký ức Hội An\" đẳng cấp thế giới.',1,NULL),(3,'Khám phá Huế 2N1Đ','Ăn uống cung đình, sông Hương',1800000.00,'2026-03-09','2026-03-15',3,1,5,0,'/anh/diemden/hue.jpg','Trở về không gian triều đình phong kiến với hệ thống Đại Nội và các lăng tẩm uy nghi (Lăng Tự Đức, Khải Định). Điểm khác biệt chính là trải nghiệm Nghe ca Huế trên sông Hương và thưởng thức \"Cơm Vua\" - nơi quý khách được hóa thân thành hoàng thân quốc thích trong bộ lễ phục triều đình.',1,NULL),(4,'Hạ Long Bay 2N1Đ','Du thuyền Vịnh Hạ Long',3000000.00,'2026-03-03','2026-03-04',4,4,6,0,'/anh/diemden/halong.jpg','Tận hưởng kỳ nghỉ dưỡng thượng lưu trên Du thuyền 5 sao. Quý khách sẽ được chèo thuyền Kayak xuyên qua các hang động đá vôi nghìn năm tuổi, đón bình minh với bài tập Tai-chi trên boong tàu và thưởng thức tiệc hải sản tươi sống giữa lòng di sản thiên nhiên thế giới.',NULL,NULL),(5,'Nha Trang Beach Relax 3N','Tắm biển, lặn ngắm san hô',3500000.00,'2026-03-03','2026-03-05',5,3,7,0,'/anh/diemden/nhatrang.jpg','Sự kết hợp hoàn hảo giữa nhịp sống phố thị và sự tĩnh lặng của biển khơi. Tour tập trung vào việc tối ưu thời gian để quý khách vừa có thể check-in những biểu tượng của Thủ đô, vừa kịp lúc bắt trọn khoảnh khắc hoàng hôn buông trên vịnh biển từ những góc nhìn đẹp nhất.',NULL,NULL),(6,'Phú Quốc Resort 4N','Resort 5*, câu cá, lặn',7000000.00,'2026-03-14','2026-03-16',6,9,8,0,'/anh/diemden/hue.jpg','Nghỉ dưỡng tại những bãi cát trắng mịn nhất Việt Nam. Chương trình tập trung vào trải nghiệm cá nhân hóa tại VinWonders, tắm bùn khoáng nóng thư giãn phục hồi sức khỏe và tham gia các trò chơi thể thao nước cảm giác mạnh như dù lượn, lặn ngắm san hô tại Vịnh San Hô.',NULL,NULL),(7,'Sa Pa Trek 2N','Trekking bản làng, cáp treo',2000000.00,'2025-05-30','2025-06-01',7,7,9,0,'/anh/diemden/sapa.jpg','Một hành trình nghỉ dưỡng thực thụ tại các resort cao cấp. Điểm nhấn là chuyến Cáp treo vượt biển dài nhất thế giới đi hòn Thơm, khám phá \"Thành phố không ngủ\" Grand World và thưởng thức đặc sản bún quậy trứ danh cùng rượu sim nồng nàn.',NULL,NULL),(8,'Cần Thơ Miền Tây 2N1Đ','Chợ nổi, miệt vườn',1600000.00,'2026-03-04','2026-03-06',8,1,10,0,'/anh/diemden/cantho.jpg','Sự giao thoa giữa hai vùng biển đẹp nhất Việt Nam. Quý khách sẽ thấy được sự khác biệt giữa vẻ đẹp hiện đại, năng động của Đà Nẵng và nét hoang sơ, thơ mộng của Phú Quốc. Đây là tour dành cho những tín đồ \"cuồng biển\" muốn chinh phục mọi cung đường xanh.',1,NULL),(9,'Beijing City Tour 4N','Tường thành, Tử Cấm Thành',1650000.00,'2025-10-01','2025-10-05',9,5,11,0,'/anh/diemden/thuonghai.jpg','Chạm tay vào kỳ quan Vạn Lý Trường Thành, khám phá sự huyền bí của Tử Cấm Thành và thưởng thức món Vịt quay Bắc Kinh nguyên bản trong không gian cung đình xưa.',NULL,NULL),(10,'Shanghai Highlights 3N','Tham quan Thượng Hải, Bến Thượng Hải',1300000.00,'2026-03-11','2026-03-13',10,5,12,1,'/anh/chuyendi/shanghai.jpg','Tập trung vào nhịp sống sôi động bậc nhất Trung Hoa. Dạo bước trên phố đi bộ Nam Kinh, chiêm bái Dự Viên và tận hưởng sự lộng lẫy của các tòa nhà chọc trời về đêm.',NULL,NULL),(11,'Zhangjiajie Nature 3N','Thiên nhiên Trương Gia Giới',1800000.00,'2025-11-01','2025-11-03',11,5,13,0,'/anh/diemden/hue.jpg','Lạc vào hành tinh Pandora ngoài đời thực với các cột đá cao vút tại Viên Gia Giới. Trải nghiệm cảm giác mạnh trên Cầu kính Đại Hiệp Cốc và đi thang máy Bách Lộ dài nhất thế giới.',NULL,NULL),(12,'Tokyo Family 5N','Tokyo Disneyland, Asakusa',2000000.00,'2026-03-03','2026-03-07',12,6,14,0,'/anh/diemden/tokyo.jpg','Tour lý tưởng cho gia đình. Khám phá Disneyland thần tiên, giao lộ Shibuya sầm uất và trải nghiệm văn hóa Anime tại Akihabara cùng với hoạt động làm Sushi thủ công.',NULL,NULL),(13,'Kyoto Culture 3N','Đền chùa, trà đạo',1800000.00,'2026-03-03','2026-03-05',13,6,14,0,'/anh/diemden/kyoto.jpg','Tìm về sự an yên tại các ngôi đền nghìn năm tuổi như Thanh Thủy Tự (Kiyomizu-dera). Điểm nhấn là đi bộ dưới hàng ngàn cổng Torii đỏ rực tại Fushimi Inari và gặp gỡ các nàng Geisha tại phố Gion.',NULL,NULL),(14,'Osaka Food Tour 2N','Món ăn đường phố, Dotonbori',1700000.00,'2025-12-11','2025-12-12',14,6,14,0,'/anh/diemden/osaka.jpg','Thiên đường cho những tín đồ ẩm thực. Càn quét khu phố Dotonbori với Takoyaki, Okonomiyaki. Tham quan lâu đài Osaka uy nghiêm và khu vui chơi Universal Studios.',NULL,NULL),(15,'Seoul City 4N','Mua sắm Myeongdong, palaces',2800000.00,'2025-09-15','2025-09-19',15,8,15,0,'/anh/diemden/seoul.jpg','Khám phá \"Làn sóng Hallyu\" tại thủ đô Hàn Quốc. Mặc Hanbok check-in cung điện Gyeongbokgung, mua sắm tại Myeongdong và ngắm toàn cảnh thành phố từ tháp Namsan lãng mạn.',NULL,NULL),(16,'Hà Nội - Hạ Long 2N','Combo Hà Nội + Hạ Long',3200000.00,'2026-03-03','2026-03-05',4,4,6,0,'/anh/diemden/hanoi.jpg','Sự giao thoa giữa hai vùng biển đẹp nhất Việt Nam. Quý khách sẽ thấy được sự khác biệt giữa vẻ đẹp hiện đại, năng động của Đà Nẵng và nét hoang sơ, thơ mộng của Phú Quốc. Đây là tour dành cho những tín đồ \"cuồng biển\" muốn chinh phục mọi cung đường xanh.',NULL,NULL),(17,'Đà Nẵng - Phú Quốc 5N','Đà Nẵng + Phú Quốc nghỉ dưỡng',9000000.00,'2025-09-10','2025-09-14',6,2,8,0,'/anh/diemden/phuquoc.jpg','Sự giao thoa giữa hai vùng biển đẹp nhất Việt Nam. Quý khách sẽ thấy được sự khác biệt giữa vẻ đẹp hiện đại, năng động của Đà Nẵng và nét hoang sơ, thơ mộng của Phú Quốc. Đây là tour dành cho những tín đồ \"cuồng biển\" muốn chinh phục mọi cung đường xanh.',NULL,NULL),(18,'Tour Bắc Kinh - Thượng Hải 7N','Kết hợp Bắc Kinh và Thượng Hải',2200000.00,'2026-03-03','2026-03-05',9,5,12,1,'/anh/chuyendi/backinh.jpg','Hành trình kết nối giữa quá khứ và tương lai. Từ vẻ cổ kính của cung điện Bắc Kinh đến sự xa hoa, hiện đại của Bến Thượng Hải và tháp truyền hình Minh Châu Phương Đông.',NULL,NULL);
/*!40000 ALTER TABLE `chuyen_di` ENABLE KEYS */;
UNLOCK TABLES;
CREATE TABLE `ngay_khoi_hanh` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nam` int DEFAULT NULL,
  `ngay` date NOT NULL,
  `thang` int DEFAULT NULL,
  `id_chuyen_di` int NOT NULL,
  `gia_ve` double DEFAULT NULL,
  `gia_ve_di` double DEFAULT NULL,
  `gia_ve_ve` double DEFAULT NULL,
  `gio_bay_di` varchar(255) DEFAULT NULL,
  `gio_bay_ve` varchar(255) DEFAULT NULL,
  `gio_den_di` varchar(255) DEFAULT NULL,
  `gio_den_ve` varchar(255) DEFAULT NULL,
  `ma_chuyen_bay_di` varchar(255) DEFAULT NULL,
  `ma_chuyen_bay_ve` varchar(255) DEFAULT NULL,
  `ngay_ve` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4ag0i3l2vuvf5mc52pw3nk1mo` (`id_chuyen_di`,`ngay`),
  CONSTRAINT `FKde2b6vwklfoia4jx3pmb9r2m6` FOREIGN KEY (`id_chuyen_di`) REFERENCES `chuyen_di` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Dumping data for table `ngay_khoi_hanh`
--

LOCK TABLES `ngay_khoi_hanh` WRITE;
/*!40000 ALTER TABLE `ngay_khoi_hanh` DISABLE KEYS */;
INSERT INTO `ngay_khoi_hanh` VALUES (10,2026,'2026-03-09',3,2,NULL,600000,2200000,'06:45','15:45','08:15','17:42','QH460','VN116','2026-03-11'),(11,2026,'2026-03-10',3,2,NULL,2200000,2750000,'10:15','16:30','11:48','18:12','QH638','VN847','2026-03-13'),(12,2026,'2026-03-26',3,2,NULL,2800000,2000000,'09:00','18:45','10:40','20:39','VN441','VN816','2026-03-29'),(13,2026,'2026-05-02',5,1,NULL,300000,0,'14:25','22:20','16:30','00:20','VJ135','VJ162','2026-05-05'),(17,2026,'2026-03-03',3,3,NULL,1454000,1454000,'17:10','05:00','19:20','07:10','VJ1149','VJ194','2026-03-04'),(19,2026,'2026-03-03',3,12,NULL,1454000,1454000,'17:45','05:00','19:55','07:10','VJ1151','VJ194','2026-03-07'),(20,2026,'2026-03-11',3,12,NULL,1454000,1454000,'05:30','05:00','07:40','07:10','VJ197','VJ194','2026-03-14'),(21,2026,'2026-03-03',3,5,NULL,300000,0,'08:00','14:00','12:00','18:00','BUS','BUS','2026-03-05'),(22,2026,'2026-03-06',3,5,NULL,300000,0,'08:00','14:00','12:00','18:00','BUS','BUS','2026-03-09'),(23,2026,'2026-03-11',3,1,NULL,300000,0,'08:00','14:00','12:00','18:00','BUS','BUS','2026-03-13'),(26,2026,'2026-03-04',3,8,NULL,1454000,1454000,'05:30','05:00','07:40','07:10','VJ197','VJ194','2026-03-11'),(27,2026,'2026-03-06',3,18,NULL,1454000,1454000,'05:30','05:00','07:40','07:10','VJ197','VJ194','2026-03-12'),(28,2026,'2026-03-18',3,1,NULL,300000,0,'08:00','14:00','12:00','18:00','BUS','BUS','2026-03-20'),(29,2026,'2026-03-20',3,1,NULL,300000,0,'08:00','14:00','12:00','18:00','BUS','BUS','2026-03-22'),(38,2026,'2026-03-18',3,3,NULL,300000,0,'08:00','14:00','12:00','18:00','BUS','BUS','2026-03-20'),(42,2026,'2026-03-12',3,10,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2026-03-14'),(43,2026,'2026-03-17',3,3,NULL,300000,0,'08:00','14:00','12:00','18:00','BUS','BUS','2026-03-19'),(50,2026,'2026-03-13',3,3,NULL,300000,0,'08:00','14:00','12:00','18:00','BUS','BUS','2026-03-14'),(52,2026,'2026-03-13',3,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2026-03-15');
/*!40000 ALTER TABLE `ngay_khoi_hanh` ENABLE KEYS */;
UNLOCK TABLES;
CREATE TABLE `lich_trinh` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_thu` int DEFAULT NULL,
  `nghi_dem` varchar(255) DEFAULT NULL,
  `noi_dung` text,
  `so_bua_an` varchar(255) DEFAULT NULL,
  `tieu_de` varchar(255) DEFAULT NULL,
  `tour_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpnc5i4nnynpvv6u68h24chauk` (`tour_id`),
  CONSTRAINT `FKpnc5i4nnynpvv6u68h24chauk` FOREIGN KEY (`tour_id`) REFERENCES `chuyen_di` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Dumping data for table `lich_trinh`
--

LOCK TABLES `lich_trinh` WRITE;
/*!40000 ALTER TABLE `lich_trinh` DISABLE KEYS */;
INSERT INTO `lich_trinh` VALUES (1,1,'','Quý khách tập trung tại Ga đi trong nước Sân bay Cần Thơ. Hướng dẫn viên làm thủ tục cho Quý khách đáp chuyến bay đi Hà Nội. Đến sân bay Nội Bài, xe khởi hành đưa Quý khách đến thành phố biển Hạ Long qua đường cao tốc Hải Phòng – Hạ Long, trên đường ngắm cảnh Bạch Đằng Giang. Đến nơi, xe đưa Quý khách ngắm Cầu Bãi Cháy và thành phố Hạ Long lung linh về đêm.\r\n\r\nQuý khách nhận phòng khách sạn nghỉ ngơi hay tự do khám phá nhiều hoạt động dịch vụ giải trí sôi nổi tại “phố cổ” Bãi Cháy - nằm cạnh công viên Sun World Hạ Long từ những ẩm thực đường phố đến các quán cà phê siêu dễ thương như Hòn Gai Coffee & Lounge hay thoải mái bung xõa tại The Mini Bar, Brothers Pub,..\r\n\r\nNghỉ đêm tại Hạ Long','02 bữa ăn (trưa, chiều)','TP. Cần Thơ - Nội Bài (Hà Nội) - Hạ Long',1),(2,2,'Nghỉ đêm tại Ninh Bình','au khi ăn sáng, xe đưa Quý khách đến bến tàu bắt đầu hành trình du ngoạn Vịnh Hạ Long - Thắng cảnh thiên nhiên tuyệt đẹp và vô cùng sống động, được UNESCO công nhận là di sản thiên nhiên Thế giới năm 1994.\r\nĐộng Thiên Cung là một trong những động đẹp nhất ở Hạ Long. Vẻ đẹp nguy nga và lộng lẫy bởi những lớp thạch nhũ và những luồng ánh sáng lung linh.\r\nTừ trên tàu ngắm nhìn các hòn đảo lớn nhỏ trong Vịnh Hạ Long: Hòn Gà Chọi, Hòn Lư Hương.\r\nSau khi ăn trưa, Quý khách khởi hành đi Ninh Bình - vùng đất mệnh danh là “Nơi mơ đến, chốn mong về” với nhiều di tích văn hóa, thiên nhiên vô cùng đặc sắc. Đến nơi, quý khách chiêm bái Chùa Bái Đính - một quần thể chùa với nhiều kỷ lục Việt Nam như pho tượng phật Di Lặc bằng đồng nặng 80 tấn, hành lang với 500 tượng vị La Hán, tòa Bảo Tháp cao 99m…\r\n','03 bữa ăn (sáng, trưa, chiều)','Hạ Long - Ninh Bình ',1),(3,3,'Nghỉ đêm tại Hà Nội','Quý khách ăn sáng và trả phòng khách sạn. Xe đưa Quý khách đi tham quan:\r\n\r\nKhu Du Lịch Tràng An: Quý khách lên thuyền truyền thống đi tham quan thắng cảnh hệ thống núi đá vôi hùng vĩ và các thung lũng ngập nước, thông với nhau bởi các dòng suối tạo nên các hang động ngập nước quanh năm. Điểm xuyến trong không gian hoang sơ, tĩnh lặng là hình ảnh rêu phong, cổ kính của các mái đình, đền, phủ nằm nép mình dưới chân các dãy núi cao.\r\nTuyệt Tịnh Cốc: nằm giữa mảnh đất cố đô Hoa Lư (Ninh Bình), động Am Tiên ẩn mình giữa lưng chừng núi được mệnh danh là “thiên đường nơi hạ giới” và được giới trẻ gọi là Tuyệt Tịnh Cốc Việt Nam.\r\n\r\nTiếp tục hành trình, Quý khách trở về Hà Nội nhận phòng khách sạn nghỉ nghơi hoặc tự do dạo 36 phố phường Hà Nội, trải nghiệm không gian sôi nổi, náo nhiệt tại Phố Tạ Hiện hay tìm một gốc với ly cà phê quan sát phố cổ hẳn cũng rất thú vị.\r\n\r\n','3 bữa ăn(sáng-trưa-chiều)','Ninh bình -Di Sản Tràng An- Hà Nội',1),(4,1,'Sapa.','Quý khách tập trung tại sân bay Tân Sơn Nhất (Ga nội địa), hướng dẫn viên hỗ trợ khách làm thủ tục đáp chuyến bay đi Hà Nội. Đến sân bay Nội Bài, xe và HDV Vietravel đón Quý khách đi khởi hànhtheo cao tốc Hà Nội – Lào Cai đưa Quý khách đến phố núi Sapa. Trên đường, Quý khách dùng cơm trưa tại nhà hàng địa phương. Đến nơi, Quý khách tham quan:\r\n\r\nBản Cát Cát - đẹp như một bức tranh giữa vùng phố cổ Sapa, nơi đây thu hút du khách bởi cầu treo, thác nước, guồng nước và những mảng màu hoa mê hoặc du khách khi lạc bước đến đây. Thăm những nếp nhà của người Mông, Dao, Giáy trong bản, du khách sẽ không khỏi ngỡ ngàng trước vẻ đẹp mộng mị của một trong những ngôi làng cổ đẹp nhất Sapa.','02 bữa (Trưa, Chiều)','Tphcm – Sân Bay Nội Bài (Hà Nội) – Sapa ',2),(5,2,'Sapa','Quý khách dùng điểm tâm sáng tại khách sạn. xe đưa đoàn ra ga Sapa, Quý khách trải nghiệm đến khu du lịch Fansipan Legend bằng Tàu hỏa leo núi Mường Hoa hiện đại nhất Việt Nam với tổng chiều dài gần 2000m, thưởng ngoạn bức tranh phong cảnh đầy màu sắc của cánh rừng nguyên sinh, thung lũng Mường Hoa.\r\n\r\nChinh phục đỉnh núi Fansipan với độ cao 3.143m hùng vĩ bằng cáp treo (chi phí tự túc).\r\nLễ Phật tại chùa Trình hay cầu phúc lộc, bình an cho gia đình tại Bích Vân Thiền Tự trong hệ thống cảnh quan tâm linh trên đỉnh Fansipan.\r\nQuý khách dùng cơm tối tại Sapa và tự do nghỉ ngơi.','03 bữa ăn (sáng, trưa, chiều)','Sapa – Fansipan Legend – Đèo Ô Quy Hồ',2);
/*!40000 ALTER TABLE `lich_trinh` ENABLE KEYS */;
UNLOCK TABLES;
CREATE TABLE `quan_ly_cho` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_chuyen_di` int DEFAULT NULL,
  `tong_so_cho` int DEFAULT NULL,
  `da_dat` int DEFAULT NULL,
  `con_lai` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_chuyen_di` (`id_chuyen_di`),
  CONSTRAINT `quan_ly_cho_ibfk_1` FOREIGN KEY (`id_chuyen_di`) REFERENCES `chuyen_di` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Dumping data for table `quan_ly_cho`
--

LOCK TABLES `quan_ly_cho` WRITE;
/*!40000 ALTER TABLE `quan_ly_cho` DISABLE KEYS */;
INSERT INTO `quan_ly_cho` VALUES (1,1,30,5,25),(2,2,40,10,30),(3,3,20,3,17),(4,4,25,8,17),(5,5,30,12,18),(6,6,20,5,15),(7,7,18,6,12),(8,8,15,2,13),(9,9,50,20,30),(10,10,45,22,23),(11,11,25,5,20),(12,12,35,10,25);
/*!40000 ALTER TABLE `quan_ly_cho` ENABLE KEYS */;
UNLOCK TABLES;

SET FOREIGN_KEY_CHECKS=1;
