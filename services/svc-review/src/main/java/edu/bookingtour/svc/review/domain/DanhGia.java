package edu.bookingtour.svc.review.domain;
import jakarta.persistence.*;
import lombok.Getter;import lombok.Setter;
import java.time.Instant;
@Getter @Setter @Entity @Table(name = "danh_gia") public class DanhGia {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;
  @Column(name="id_chuyen_di", nullable=false) private Integer tourId;
  @Column(name="id_nguoi_dung", nullable=false) private Integer userId;
  private Integer diem;
  @Column(length = 512) private String binhLuan;
  @Column(name="ngay_danh_gia") private Instant ngayDanhGia;
  @PrePersist void p(){ if(ngayDanhGia==null) ngayDanhGia=Instant.now();}
}
