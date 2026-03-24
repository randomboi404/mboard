package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.dto.UserRequest;
import io.github.randomboi404.mboard.model.User;
import io.github.randomboi404.mboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
    
    public String register(UserRequest request) {
        String username = request.username();
        
        if (repo.findByUsername(username) != null) {
            return "Username " + username + " already exists!";
        }
        
        String encodedPassword = encoder.encode(request.password());
        
        User newUser = new User(username, encodedPassword);
        repo.save(newUser);
        
        return "User created successfully!";
    }
    
}
