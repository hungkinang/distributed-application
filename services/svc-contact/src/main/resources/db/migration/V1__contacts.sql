SET FOREIGN_KEY_CHECKS=0;
SET NAMES utf8mb4;

CREATE TABLE `contacts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `content` text,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `guest_number` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tittle` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'Hải dương','thắc mắc cách di chuyển đến điểm đón cần giải thích\r\n','2026-03-11 11:54:15.915214','minhd4360@gmail.com',2,'Nguyễn minh đức','0866147595','READ','Trợ giúp cách di chuyển','SUPPORT');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;
SET FOREIGN_KEY_CHECKS=1;
