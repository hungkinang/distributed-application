package edu.bookingtour.svc.booking.repo;

import edu.bookingtour.svc.booking.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByUserIdOrderByIdDesc(Integer userId);
}
