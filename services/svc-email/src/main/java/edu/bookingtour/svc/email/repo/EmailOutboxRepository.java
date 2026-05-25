package edu.bookingtour.svc.email.repo;

import edu.bookingtour.svc.email.domain.EmailOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmailOutboxRepository extends JpaRepository<EmailOutbox, UUID> {

    List<EmailOutbox> findTop20ByStatusOrderByCreatedAtAsc(String status);
}
