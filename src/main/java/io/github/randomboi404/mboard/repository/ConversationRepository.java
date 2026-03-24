package io.github.randomboi404.mboard.repository;

import io.github.randomboi404.mboard.model.ConversationModel;
import io.github.randomboi404.mboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationModel, String> {
    
    Optional<ConversationModel> findById(String id);
    
    List<ConversationModel> findByUsersContaining(User user);
    
}
