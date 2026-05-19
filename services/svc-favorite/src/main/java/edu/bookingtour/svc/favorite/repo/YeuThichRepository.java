package edu.bookingtour.svc.favorite.repo;
import edu.bookingtour.svc.favorite.domain.YeuThich;import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;import java.util.Optional;
public interface YeuThichRepository extends JpaRepository<YeuThich,Integer>{
 List<YeuThich> findByUserIdOrderByNgayThemDesc(Integer userId);
 Optional<YeuThich> findByUserIdAndTourId(Integer userId, Integer tourId);
 void deleteByUserIdAndTourId(Integer userId, Integer tourId);
}
