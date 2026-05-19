package edu.bookingtour.svc.booking.api.dto;

public record ReservationResponse(
        Integer id,
        Integer userId,
        Integer chuyenDiId,
        Integer soLuong,
        String trangThai,
        String hoTen,
        String email,
        String soDienThoai,
        Double tongGia,
        String ngayDat,
        String createdAt
) {}
