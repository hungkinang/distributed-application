package edu.bookingtour.svc.chat.repo;

import edu.bookingtour.svc.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findTop20BySessionKeyOrderByCreatedAtDesc(String sessionKey);
}
