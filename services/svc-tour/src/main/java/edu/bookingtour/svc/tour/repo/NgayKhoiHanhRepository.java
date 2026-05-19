package edu.bookingtour.svc.tour.repo;

import edu.bookingtour.svc.tour.domain.NgayKhoiHanh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NgayKhoiHanhRepository extends JpaRepository<NgayKhoiHanh, Integer> {

    List<NgayKhoiHanh> findByChuyenDi_IdAndThangAndNam(Integer chuyenDiId, Integer thang, Integer nam);

    Optional<NgayKhoiHanh> findByChuyenDi_IdAndNgay(Integer chuyenDiId, LocalDate ngay);

    List<NgayKhoiHanh> findByChuyenDi_Id(Integer chuyenDiId);
}
