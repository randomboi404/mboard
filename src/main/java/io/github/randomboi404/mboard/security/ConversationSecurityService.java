package io.github.randomboi404.mboard.security;

import io.github.randomboi404.mboard.model.ConversationModel;
import io.github.randomboi404.mboard.model.User;
import io.github.randomboi404.mboard.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationSecurityService {
    
    private final ConversationRepository conversationRepo;
    
    public boolean canUserAccessConversation(User user, String conversationId) {
        ConversationModel conversationModel = conversationRepo.findById(conversationId)
            .orElse(null);
        
        return conversationModel != null && conversationModel.getUsers().contains(user);
    }
    
}
