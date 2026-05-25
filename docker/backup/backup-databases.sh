#!/bin/sh
set -eu

BACKUP_DIR="${BACKUP_DIR:-/backups}"
MYSQL_HOST="${MYSQL_HOST:-mysql}"
MYSQL_USER="${MYSQL_USER:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-root}"
TIMESTAMP="$(date +%Y%m%d_%H%M%S)"
ARCHIVE="${BACKUP_DIR}/bookingtour_all_${TIMESTAMP}.sql.gz"

mkdir -p "${BACKUP_DIR}"

DATABASES="auth_db user_db tour_db booking_db payment_db email_db notification_db contact_db review_db favorite_db flight_db news_db bookingtour"

echo "[backup] starting mysqldump -> ${ARCHIVE}"
mysqldump -h "${MYSQL_HOST}" -u"${MYSQL_USER}" -p"${MYSQL_PASSWORD}" \
  --single-transaction --routines --triggers --databases ${DATABASES} \
  | gzip > "${ARCHIVE}"

echo "[backup] done size=$(du -h "${ARCHIVE}" | cut -f1)"

# Giữ tối đa 7 bản gần nhất
ls -1t "${BACKUP_DIR}"/bookingtour_all_*.sql.gz 2>/dev/null | tail -n +8 | xargs -r rm -f
