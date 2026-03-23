package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.model.Message;
import io.github.randomboi404.mboard.service.MessageService;
import io.github.randomboi404.mboard.dto.MessageRequest;
import io.github.randomboi404.mboard.dto.TypingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequiredArgsConstructor
public class MessageController {
    
    private final MessageService messageService;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    
    @GetMapping("/api/v1/messages")
    public List<Message> getMessages() {
        return messageService.getMessages();
    }
    
    @GetMapping(value = "/api/v2/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamMessages() {
        SseEmitter emitter = new SseEmitter(300_000L);
        
        this.emitters.add(emitter);
        
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
        emitter.onError((e) -> this.emitters.remove(emitter));

        return emitter;
    }
    
    @PostMapping("/api/v2/typing")
    public void typing(@RequestBody TypingRequest request) {
        SseEventBuilder builder = SseEmitter.event()
            .name("typing")
            .data(request);
        
        this.sendMessage(builder);
    }
    
    @PostMapping("/api/v1/messages")
    public void saveMessage(@RequestBody MessageRequest request) {
        String username = request.username();
        String msgContent = request.message();
        String dateTime = Instant.now().toString();
        
        Message message = new Message(username, msgContent, dateTime);
        messageService.saveMessage(message);
        
        SseEventBuilder builder = SseEmitter.event()
            .name("message")
            .data(message);
        
        this.sendMessage(builder);
    }
    
    private void sendMessage(SseEventBuilder builder) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(builder);
            } catch (IOException e) {
                this.emitters.remove(emitter);
            }
        }
    }
}
