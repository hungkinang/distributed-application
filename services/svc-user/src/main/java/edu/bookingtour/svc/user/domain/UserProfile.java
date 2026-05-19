package edu.bookingtour.svc.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "ho_ten")
    private String hoTen;

    private String email;

    @Column(name = "number")
    private String number;

    @Column(name = "anh_dai_dien")
    private String anhDaiDien;
}
