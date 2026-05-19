package edu.bookingtour.svc.tour.repo;

import edu.bookingtour.svc.tour.domain.QuanLyCho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuanLyChoRepository extends JpaRepository<QuanLyCho, Integer> {

    Optional<QuanLyCho> findByIdChuyenDi_Id(Integer tourId);
}
