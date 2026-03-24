package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.model.ConversationModel;
import io.github.randomboi404.mboard.model.Message;
import io.github.randomboi404.mboard.model.UserPrincipal;
import io.github.randomboi404.mboard.dto.MessageRequest;
import io.github.randomboi404.mboard.repository.MessageRepository;
import io.github.randomboi404.mboard.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repo;
    private final ConversationRepository conversationRepo;
    private final NotificationService notificationService;

    public Page<Message> getMessages(String conversationId, Pageable pageable) {
        return repo.findByConversationIdOrderByDateTimeDesc(conversationId, pageable);
    }

    public void saveMessage(Message message) {
        repo.save(message);
    }

    public void deleteMessage(String id) {
        repo.deleteById(id);
    }

    public void processAndBroadcast(UserPrincipal userPrincipal, MessageRequest request, String conversationId) {
        ConversationModel conversationModel = conversationRepo.getReferenceById(conversationId);
        
        Message message = new Message(
                conversationModel,
                userPrincipal.getUser(),
                request.message()
        );

        this.saveMessage(message);

        notificationService.broadcast(
                conversationId,
                SseEmitter.event()
                        .name("message")
                        .data(message)
        );
    }
    
}
