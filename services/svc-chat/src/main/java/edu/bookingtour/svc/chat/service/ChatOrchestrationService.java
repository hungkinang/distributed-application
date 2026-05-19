package edu.bookingtour.svc.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bookingtour.svc.chat.config.GeminiProperties;
import edu.bookingtour.svc.chat.domain.ChatMessage;
import edu.bookingtour.svc.chat.integration.TourContextFetcher;
import edu.bookingtour.svc.chat.repo.ChatMessageRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ChatOrchestrationService {

    private final GeminiProperties gemini;
    private final TourContextFetcher tours;
    private final ChatMessageRepository messages;
    private final RestTemplate rt = new RestTemplate();
    private final ObjectMapper json = new ObjectMapper();

    public ChatOrchestrationService(
            GeminiProperties gemini, TourContextFetcher tours, ChatMessageRepository messages) {
        this.gemini = gemini;
        this.tours = tours;
        this.messages = messages;
    }

    public Map<String, String> handle(String rawMessage, String sessionKeyHint, Integer userId) {
        String session =
                sessionKeyHint != null && !sessionKeyHint.isBlank() ? sessionKeyHint : UUID.randomUUID().toString();

        String apiKey = gemini.getKey();
        if (apiKey == null || apiKey.isBlank()) {
            return Map.of(
                    "reply",
                    "Xin lỗi, Chatbot đang bảo trì (thiếu GEMINI_API_KEY).",
                    "sessionKey",
                    session);
        }

        persist(session, userId, "USER", rawMessage);

        String systemPrompt = buildSystemPrompt(rawMessage);
        String replyText;
        try {
            replyText = callGemini(systemPrompt, rawMessage, apiKey);
        } catch (Exception ex) {
            replyText = "Đã có lỗi khi gọi AI: " + ex.getMessage();
        }

        persist(session, userId, "ASSISTANT", replyText);

        return Map.of("reply", replyText, "sessionKey", session);
    }

    private void persist(String session, Integer userId, String role, String content) {
        ChatMessage row = new ChatMessage();
        row.setSessionKey(session);
        row.setUserId(userId);
        row.setRole(role);
        row.setContent(content);
        messages.save(row);
    }

    private String buildSystemPrompt(String userMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("Bạn là 'Zaki AI' - tư vấn du lịch ZakiBooking. Trả lời ngắn, thân thiện, có emoji vừa phải, Markdown bullet.\n");
        sb.append("Luôn gợi ý link xem chi tiết dạng: [Xem chi tiết](/tour/ID) khi phù hợp.\n\n");
        sb.append("--- TOUR đang hoạt động (từ hệ thống) ---\n");
        List<Map<String, Object>> list = tours.loadTours(15);
        for (Map<String, Object> t : list) {
            sb.append("- ID ")
                    .append(t.get("id"))
                    .append(": ")
                    .append(t.get("tieuDe"))
                    .append(" | Giá: ")
                    .append(t.get("gia"))
                    .append(" | Điểm đến: ")
                    .append(t.get("diemDen"))
                    .append("\n  Mô tả: ")
                    .append(t.get("moTaShort"))
                    .append("\n");
        }
        if (userMessage != null && userMessage.contains("@")) {
            sb.append("\n(Người dùng có thể đang nhắc email để tra cứu đơn — trong microservice chỉ có dữ liệu tour).\n");
        }
        sb.append("\nHotline: +84 866147595");
        return sb.toString();
    }

    private String callGemini(String systemPrompt, String userMessage, String apiKey) throws Exception {
        String url = gemini.getApi().getUrl();
        if (url.contains("?")) {
            url = url + "&key=" + apiKey;
        } else {
            url = url + "?key=" + apiKey;
        }

        Map<String, Object> parts = Map.of("text", systemPrompt + "\n\nUser: " + userMessage);
        Map<String, Object> contents = Map.of("parts", List.of(parts));

        Map<String, Object> payload = Map.of("contents", List.of(contents));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> req = new HttpEntity<>(payload, headers);

        String body = rt.postForObject(url, req, String.class);
        JsonNode root = json.readTree(body);
        JsonNode text =
                root.path("candidates").path(0).path("content").path("parts").path(0).path("text");
        return text.isMissingNode() ? "Không nhận được phản hồi hợp lệ." : text.asText();
    }
}
