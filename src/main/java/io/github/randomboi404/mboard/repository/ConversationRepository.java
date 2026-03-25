package io.github.randomboi404.mboard.repository;

import io.github.randomboi404.mboard.model.Conversation;
import io.github.randomboi404.mboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {
    
    Optional<Conversation> findById(String id);
    
    List<Conversation> findByUsersContaining(User user);
    
}
