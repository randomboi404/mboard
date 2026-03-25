package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.dto.UserRegisterRequest;
import io.github.randomboi404.mboard.model.User;
import io.github.randomboi404.mboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    
    public String register(UserRegisterRequest request) {
        String username = request.username();
        
        if (repo.findByUsername(username) != null) {
            return "Username " + username + " already exists!";
        }
        
        String encodedPassword = encoder.encode(request.password());
        
        User newUser = new User(username, encodedPassword);
        repo.save(newUser);
        
        return "User created successfully!";
    }
    
    public List<User> searchUsers(String query) {  
        return repo.findByUsernameContainingIgnoreCase(query);
    }
    
}
