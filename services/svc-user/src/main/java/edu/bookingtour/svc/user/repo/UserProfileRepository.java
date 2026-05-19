package edu.bookingtour.svc.user.repo;

import edu.bookingtour.svc.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {}
