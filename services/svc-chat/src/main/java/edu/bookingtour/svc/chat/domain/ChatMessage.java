package edu.bookingtour.svc.chat.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_key", nullable = false, length = 64)
    private String sessionKey;

    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false, length = 16)
    private String role;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(name = "created_at")
    private Instant createdAt;

    @jakarta.persistence.PrePersist
    void pre() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
