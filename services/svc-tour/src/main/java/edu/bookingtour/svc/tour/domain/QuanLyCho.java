package edu.bookingtour.svc.tour.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quan_ly_cho")
public class QuanLyCho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chuyen_di")
    private ChuyenDi idChuyenDi;

    @Column(name = "tong_so_cho")
    private Integer tongSoCho;

    @Column(name = "da_dat")
    private Integer daDat;

    @Column(name = "con_lai")
    private Integer conLai;

    /** Optimistic lock — tránh race khi nhiều booking cùng tour. */
    @Version
    @Column(name = "version")
    private Long version;
}
