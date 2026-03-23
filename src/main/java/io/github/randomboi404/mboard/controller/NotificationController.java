package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.dto.TypingRequest;
import io.github.randomboi404.mboard.dto.ActiveCountRequest;
import io.github.randomboi404.mboard.service.NotificationService;
import jakarta.servlet.http.HttpSession; 
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notifService;

    @GetMapping(value = "/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamMessages(HttpSession session) {
        session.setAttribute("initialized", true);
        String sessionId = session.getId();
        
        return notifService.getNewEmitter(sessionId);
    }
    
    @GetMapping("/users/active")
    public ActiveCountRequest getActiveUsersCount() {
        return new ActiveCountRequest(notifService.getEmittersCount());
    }

    @PostMapping("/typing")
    public void typing(@RequestBody TypingRequest request) {
        SseEventBuilder builder = SseEmitter.event()
                .name("typing")
                .data(request);

        notifService.broadcast(builder);
    }
    
}
