package io.github.randomboi404.mboard.repository;

import io.github.randomboi404.mboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    User findByUsername(String username);
    
}
