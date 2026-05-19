package edu.bookingtour.svc.payment.repo;
import edu.bookingtour.svc.payment.domain.VnpCallbackLog;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VnpCallbackLogRepository extends JpaRepository<VnpCallbackLog, Long> {}
