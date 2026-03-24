package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.dto.ActiveCountResponse;
import io.github.randomboi404.mboard.model.UserPrincipal;
import io.github.randomboi404.mboard.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;
import java.util.Map;

@RestController
@RequestMapping("/api/chat/{conversationId}")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notifService;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("@conversationSecurityService.canUserAccessConversation(#userPrincipal.user, #conversationId)")
    public SseEmitter streamMessages(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String conversationId,
            HttpSession session
    ) {
        session.setAttribute("initialized", true);
        return notifService.getNewEmitter(conversationId, session.getId());
    }

    @GetMapping("/users/active")
    @PreAuthorize("@conversationSecurityService.canUserAccessConversation(#userPrincipal.user, #conversationId)")
    public ActiveCountResponse getActiveUsersCount(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String conversationId
    ) {
        return new ActiveCountResponse(notifService.getActiveEmittersCount(conversationId));
    }

    @PostMapping("/typing")
    @PreAuthorize("@conversationSecurityService.canUserAccessConversation(#userPrincipal.user, #conversationId)")
    public void typing(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String conversationId,
            @RequestBody boolean isTyping
    ) {
        Map<String, Object> data = Map.of(
                "username", userPrincipal.getUsername(),
                "isTyping", isTyping,
                "conversationId", conversationId
        );

        SseEventBuilder builder = SseEmitter.event()
                .name("typing")
                .data(data);

        notifService.broadcast(conversationId, builder);
    }
    
}
