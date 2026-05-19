package edu.bookingtour.svc.review.repo;
import edu.bookingtour.svc.review.domain.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
  List<DanhGia> findByTourIdOrderByNgayDanhGiaDesc(Integer tourId);
}
