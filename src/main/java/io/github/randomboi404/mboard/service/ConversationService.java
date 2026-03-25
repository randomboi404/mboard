package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.model.Conversation;
import io.github.randomboi404.mboard.model.User;
import io.github.randomboi404.mboard.repository.ConversationRepository;
import io.github.randomboi404.mboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
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
    public Conversation createConversation(String title, Set<String> userIds, User creator) 
            throws ResponseStatusException {
        
        Set<User> participants = new HashSet<>(userRepo.findAllById(userIds));

        if (participants.size() != userIds.size()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Your list of IDs contains some invalid IDs."
            );
        }

        participants.add(creator);

        Conversation conversation = new Conversation();
        conversation.setTitle(title);
        conversation.setUsers(participants);

        return conversationRepo.save(conversation);
    }

    public List<Conversation> getConversationsForUser(User user) {
        return conversationRepo.findByUsersContaining(user);
    }

    public Optional<Conversation> getConversation(String id) {
        return conversationRepo.findById(id);
    }
    
}
