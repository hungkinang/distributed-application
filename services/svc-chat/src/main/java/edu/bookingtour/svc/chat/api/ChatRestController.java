package edu.bookingtour.svc.chat.api;

import edu.bookingtour.svc.chat.service.ChatOrchestrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatOrchestrationService orchestration;

    public ChatRestController(ChatOrchestrationService orchestration) {
        this.orchestration = orchestration;
    }

    /** Tương thích frontend monolith cũ: POST /api/chat { "message": "..." } → { "reply": "..." } */
    @PostMapping
    public ResponseEntity<Map<String, String>> chatLegacy(@RequestBody Map<String, String> body) {
        String msg = body.getOrDefault("message", "");
        String sess = body.get("sessionKey");
        Map<String, String> out = orchestration.handle(msg, sess, null);
        return ResponseEntity.ok(Map.of("reply", out.get("reply")));
    }

    @PostMapping("/session")
    public Map<String, String> chatFull(@RequestBody Map<String, String> body) {
        String msg = body.getOrDefault("message", "");
        String sess = body.get("sessionKey");
        return orchestration.handle(msg, sess, null);
    }
}
