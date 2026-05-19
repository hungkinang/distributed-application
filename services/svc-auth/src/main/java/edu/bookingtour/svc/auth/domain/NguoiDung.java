package edu.bookingtour.svc.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "nguoi_dung")
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;

    @Column(name = "email")
    private String email;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "vai_tro")
    private String vaiTro;

    @Column(name = "ngay_tao")
    private Instant ngayTao;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "number")
    private String number;

    @Column(name = "provider")
    private String provider;

    @Column(name = "anh_dai_dien")
    private String anhDaiDien;

    @PrePersist
    protected void onCreate() {
        if (ngayTao == null) {
            ngayTao = Instant.now();
        }
    }
}
