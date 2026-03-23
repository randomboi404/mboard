package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.model.Message;
import io.github.randomboi404.mboard.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    
    private final MessageService messageService;
    
    public record MessageRequest(String username, String message) {}
    
    @GetMapping("/api/v1/messages")
    public List<Message> getMessages() {
        return messageService.getMessages();
    }
    
    @PostMapping("/api/v1/messages")
    public void saveMessage(@RequestBody MessageRequest request) {
        String username = request.username();
        String msgContent = request.message();
        String dateTime = Instant.now().toString();
        
        Message message = new Message(username, msgContent, dateTime);
        messageService.saveMessage(message);
    }
    
}
