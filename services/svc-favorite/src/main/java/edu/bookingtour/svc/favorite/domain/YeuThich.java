package edu.bookingtour.svc.favorite.domain;
import jakarta.persistence.*;import lombok.Getter;import lombok.Setter;import java.time.Instant;
@Getter @Setter @Entity @Table(name="yeu_thich") public class YeuThich {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer id;
 @Column(name="id_nguoi_dung", nullable=false) private Integer userId;
 @Column(name="id_chuyen_di", nullable=false) private Integer tourId;
 @Column(name="ngay_them") private Instant ngayThem;
 @PrePersist void p(){if(ngayThem==null) ngayThem=Instant.now();}
}
