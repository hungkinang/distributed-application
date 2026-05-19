package edu.bookingtour.svc.news.repo;

import edu.bookingtour.svc.news.domain.NewsFetchLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFetchLogRepository extends JpaRepository<NewsFetchLog, Long> {}
