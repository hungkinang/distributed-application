package edu.bookingtour.svc.flight.repo;

import edu.bookingtour.svc.flight.domain.FlightLookupLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightLookupLogRepository extends JpaRepository<FlightLookupLog, Long> {}
