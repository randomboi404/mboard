package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.dto.MessageRequest;
import io.github.randomboi404.mboard.model.Message;
import io.github.randomboi404.mboard.model.UserPrincipal;
import io.github.randomboi404.mboard.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat/{conversationId}")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private static final int PAGE_SIZE = 20;

    @GetMapping
    @PreAuthorize("@conversationSecurityService.canUserAccessConversation(#userPrincipal.user, #conversationId)")
    public Page<Message> getMessagePage(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String conversationId,
            @RequestParam(defaultValue = "0") int page
    ) {
        return messageService.getMessages(
                conversationId,
                PageRequest.of(page, PAGE_SIZE, Sort.by("dateTime").descending())
        );
    }

    @PostMapping
    @PreAuthorize("@conversationSecurityService.canUserAccessConversation(#userPrincipal.user, #conversationId)")
    public void saveMessage(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String conversationId,
            @Valid @RequestBody MessageRequest request
    ) {
        messageService.processAndBroadcast(userPrincipal, request, conversationId);
    }
    
}
