package io.github.randomboi404.mboard.controller;  

import io.github.randomboi404.mboard.model.User;
import io.github.randomboi404.mboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String username) {
        return userService.searchUsers(username);
    }
    
}
