package edu.bookingtour.svc.auth.repo;

import edu.bookingtour.svc.auth.domain.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {

    Optional<NguoiDung> findByTenDangNhap(String tenDangNhap);

    Optional<NguoiDung> findByEmail(String email);
}
