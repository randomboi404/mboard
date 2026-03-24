package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.model.ConversationModel;
import io.github.randomboi404.mboard.model.User;
import io.github.randomboi404.mboard.repository.ConversationRepository;
import io.github.randomboi404.mboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepo;
    private final UserRepository userRepo;

    @Transactional
    public ConversationModel createConversation(String title, Set<String> userIds, User creator) {
        Set<User> participants = new HashSet<>(userRepo.findAllById(userIds));
        participants.add(creator);
        
        ConversationModel conversation = new ConversationModel();
        
        if (title == null || title.isBlank()) {
            conversation.setTitle("New Chat");
        } else {
            conversation.setTitle(title);
        }
        
        conversation.setUsers(participants);
        return conversationRepo.save(conversation);
    }

    public List<ConversationModel> getConversationsForUser(User user) {
        return conversationRepo.findByUsersContaining(user);
    }

    public Optional<ConversationModel> getConversation(String id) {
        return conversationRepo.findById(id);
    }
    
}
