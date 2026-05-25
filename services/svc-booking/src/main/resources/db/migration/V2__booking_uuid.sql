ALTER TABLE dat_cho
    ADD COLUMN booking_uuid BINARY(16) NULL AFTER id;

UPDATE dat_cho
SET booking_uuid = UNHEX(REPLACE(UUID(), '-', ''))
WHERE booking_uuid IS NULL;

ALTER TABLE dat_cho
    MODIFY booking_uuid BINARY(16) NOT NULL,
    ADD UNIQUE KEY uk_dat_cho_uuid (booking_uuid);
