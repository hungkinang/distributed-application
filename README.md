# BookingTour — Distributed Microservices

Hệ thống đặt tour du lịch: **Java 17**, **Spring Boot 3.x**, **Maven**, kiến trúc microservices production-oriented.

**Phạm vi hiện tại:** chỉ chức năng **User** (đã gỡ **Admin panel** và **Chatbot AI**).

---

## Cấu trúc thư mục

```text
distributed-application/
├── bookingtour-ms-common/     # JWT, ApiResponse, hợp đồng RabbitMQ
├── bookingtour-app/          # Monolith Thymeleaf (UI user)
├── services/
│   ├── eureka-server/        # Service registry
│   ├── api-gateway/          # Gateway + JWT + Resilience4j
│   ├── svc-auth/             # Authentication
│   ├── svc-user/
│   ├── svc-tour/             # Optimistic lock chỗ ngồi
│   ├── svc-booking/          # REST + RabbitMQ producer
│   ├── svc-payment/
│   ├── svc-email/            # RabbitMQ consumer, @Async, Quartz
│   ├── svc-notification/
│   └── svc-{contact,review,favorite,flight,news}/  # User APIs bổ sung
├── docker/
│   ├── Dockerfile.service
│   ├── mysql/init-databases.sql
│   └── backup/backup-databases.sh
├── docker-compose.yml
└── docs/ARCHITECTURE.md
```

---

## Công nghệ bắt buộc

| Nhóm | Công nghệ | Vị trí triển khai |
|------|-----------|-------------------|
| Đa luồng | Thread pool, `@Async`, Quartz | `svc-email` |
| Messaging | REST + RabbitMQ | `svc-booking` → `svc-email`, `svc-notification` |
| Định danh | UUID, JWT, Eureka, DNS Docker | Gateway, entities, `docker-compose.yml` |
| Đồng bộ | RabbitMQ + `@Version` | Tour seats, booking events |
| Sao lưu | mysqldump + cron + volume | `db-backup` service |
| Chịu lỗi | Resilience4j, Actuator, `restart: unless-stopped` | Gateway, `TourInventoryClient` |

Chi tiết kiến trúc: [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)

---

## Chạy bằng Docker

### Yêu cầu

- Docker Desktop / Engine 24+
- Docker Compose v2

### Lệnh

```bash
docker compose -f docker-compose.yml up --build -d
```

### Endpoint

| Dịch vụ | URL |
|---------|-----|
| API Gateway | http://localhost:8088 |
| Eureka | http://localhost:8761 |
| RabbitMQ Management | http://localhost:15672 (guest/guest) |
| MySQL | localhost:3307 (root/root) |

### Health

```bash
curl http://localhost:8088/actuator/health
curl http://localhost:8761/actuator/health
```

---

## API mẫu (qua Gateway)

### Đăng ký / đăng nhập

```bash
curl -X POST http://localhost:8088/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"tenDangNhap":"user1","email":"u1@test.com","matKhau":"secret123","hoTen":"User One"}'

curl -X POST http://localhost:8088/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"secret123"}'
```

### Đặt tour (JWT bắt buộc)

```bash
export TOKEN="<accessToken từ login>"

curl -X POST http://localhost:8088/api/bookings/reservations \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "chuyenDiId": 1,
    "soLuong": 2,
    "hoTen": "Nguyen Van A",
    "email": "a@test.com",
    "soDienThoai": "0900000000",
    "tongGia": 5000000
  }'
```

Sau khi đặt thành công, kiểm tra log:

- `svc-booking`: publish `booking.created`
- `svc-email`: `[email-mock]` hoặc gửi SMTP thật
- `svc-notification`: bản ghi trong `notification_db`

---

## Monolith UI (tùy chọn)

```bash
./mvnw -pl bookingtour-app spring-boot:run
# hoặc
docker compose -f compose.monolith.yaml up --build
```

Monolith dùng **session**; stack microservice dùng **JWT** — DB tách biệt.

---

## Backup & restore MySQL

**Backup tự động:** container `db-backup` chạy script mỗi 24h, lưu vào volume `mysql_backup`.

**Backup thủ công:**

```bash
docker compose exec db-backup /scripts/backup-databases.sh
```

**Restore:**

```bash
gunzip -c ./backups/bookingtour_all_YYYYMMDD_HHMMSS.sql.gz \
  | docker compose exec -T mysql mysql -uroot -proot
```

---

## Build Maven (local)

```bash
./mvnw clean package -DskipTests
./mvnw -pl services/svc-booking -am spring-boot:run
```

Biến môi trường quan trọng: `JWT_SECRET`, `INTERNAL_SERVICE_TOKEN`, `EUREKA_URL`, `RABBITMQ_HOST`.

---

## Best practices đã áp dụng

- **Clean architecture:** API → Application Service → Domain → Repository
- **DTO + validation** trên REST controllers
- **ApiResponse** chuẩn hóa JSON
- **Global exception handler** (mẫu trên `svc-email`)
- **Database per service** + Flyway migrations
- **Không hardcode host** — dùng tên service Docker + Eureka `lb://`
- **Secrets qua env** — không commit production secrets

---

## Đã gỡ (theo yêu cầu)

- Toàn bộ **Admin** (monolith `/admin/**`, `svc-admin`)
- **Chatbot** (UI, `/api/chat`, `svc-chat`, Gemini)

---

*BookingTour — Distributed Systems Demo*
