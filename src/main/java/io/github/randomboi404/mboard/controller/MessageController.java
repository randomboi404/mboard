package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.model.Message;
import io.github.randomboi404.mboard.service.MessageService;
import lombok.RequiredArgsConstructor; // Fixed import
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    
    private final MessageService messageService;
    
    @GetMapping("/api/v1/messages")
    public List<Message> getMessages() {
        return messageService.getMessages();
    }
    
}
