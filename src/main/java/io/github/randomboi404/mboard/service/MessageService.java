package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.dto.MessageRequest;
import io.github.randomboi404.mboard.model.Message;
import io.github.randomboi404.mboard.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository msgRepo;
    private final NotificationService notificationService;

    public Page<Message> getMessages(Pageable pageable) {
        return msgRepo.findAll(pageable);
    }

    public void saveMessage(Message message) {
        msgRepo.saveAndFlush(message);
    }

    public void deleteMessage(String id) {
        msgRepo.deleteById(id);
    }

    public void processAndBroadcast(MessageRequest request) {
        Message message = new Message(
                request.username(),
                request.message(),
                Instant.now().toString()
        );

        this.saveMessage(message);

        notificationService.broadcast(
                SseEmitter.event()
                        .name("message")
                        .data(message)
        );
    }
    
}
