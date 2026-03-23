package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.model.Message;
import io.github.randomboi404.mboard.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageRepository msgRepo;
    
    public List<Message> getMessages() {
        return msgRepo.findAll();
    }
    
    public void saveMessage(Message message) {
        msgRepo.saveAndFlush(message);
    }
    
    public void deleteMessage(String id) {
        msgRepo.deleteById(id);
    }
    
}
