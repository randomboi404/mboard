package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.dto.ActiveCountResponse;
import io.github.randomboi404.mboard.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notifService;

    @GetMapping(value = "/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamMessages(HttpSession session) {
        session.setAttribute("initialized", true);
        return notifService.getNewEmitter(session.getId());
    }

    @GetMapping("/users/active")
    public ActiveCountResponse getActiveUsersCount() {
        return new ActiveCountResponse(notifService.getEmittersCount());
    }

    @PostMapping("/typing")
    public void typing(@AuthenticationPrincipal UserDetails userPrincipal, @RequestBody boolean isTyping) {
        Map<String, Object> data = Map.of(
            "username", userPrincipal.getUsername(),
            "isTyping", isTyping
        );

        SseEventBuilder builder = SseEmitter.event()
                .name("typing")
                .data(data);

        notifService.broadcast(builder);
    }
    
}
