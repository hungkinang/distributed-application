package edu.bookingtour.svc.tour.repo;

import edu.bookingtour.svc.tour.domain.LichTrinh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LichTrinhRepository extends JpaRepository<LichTrinh, Integer> {

    List<LichTrinh> findByTourIdOrderByNgayThuAsc(Integer tourId);
}
