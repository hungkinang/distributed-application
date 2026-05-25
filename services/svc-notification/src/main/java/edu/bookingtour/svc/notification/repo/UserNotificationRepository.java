package edu.bookingtour.svc.notification.repo;

import edu.bookingtour.svc.notification.domain.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserNotificationRepository extends JpaRepository<UserNotification, UUID> {}
