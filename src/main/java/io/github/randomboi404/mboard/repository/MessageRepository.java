package io.github.randomboi404.mboard.repository;

import io.github.randomboi404.mboard.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    
}
