package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.dto.ConversationRequest;
import io.github.randomboi404.mboard.model.Conversation;
import io.github.randomboi404.mboard.model.UserPrincipal;
import io.github.randomboi404.mboard.service.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController  
@RequestMapping("/api/chat")  
@RequiredArgsConstructor  
public class ConversationController {  
  
    private final ConversationService conversationService;  
  
    @GetMapping  
    public List<Conversation> listConversations(  
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {  
        return conversationService.getConversationsForUser(userPrincipal.getUser());  
    }  
  
    @PostMapping  
    public Conversation createConversation(  
            @AuthenticationPrincipal UserPrincipal userPrincipal,  
            @Valid @RequestBody ConversationRequest request
    ) {  
        return conversationService.createConversation(  
                request.title(), request.participantIds(), userPrincipal.getUser()
        );  
    }  
  
    @GetMapping("/{id}")  
    @PreAuthorize("@conversationSecurityService.canUserAccessConversation(#userPrincipal.user, #id)")  
    public Conversation getConversation(  
            @AuthenticationPrincipal UserPrincipal userPrincipal,  
            @PathVariable String id
    ) {  
        return conversationService.getConversation(id)  
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );  
    }
    
}
