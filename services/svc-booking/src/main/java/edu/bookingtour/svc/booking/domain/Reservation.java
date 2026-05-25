package edu.bookingtour.svc.booking.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dat_cho")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "booking_uuid", columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID bookingUuid;

    @Column(name = "id_nguoi_dung")
    private Integer userId;

    @Column(name = "id_chuyen_di", nullable = false)
    private Integer chuyenDiId;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "ngay_dat")
    private LocalDate ngayDat;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "trang_thai", length = 50)
    private String trangThai;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "email")
    private String email;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "dia_chi", length = 500)
    private String diaChi;

    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;

    @Column(name = "id_ma_giam_gia")
    private Integer maGiamGiaId;

    @Column(name = "tong_gia")
    private Double tongGia;
}
