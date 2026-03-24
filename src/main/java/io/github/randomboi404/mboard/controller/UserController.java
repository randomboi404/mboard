package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.model.User;
import io.github.randomboi404.mboard.service.UserService;
import io.github.randomboi404.mboard.dto.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @PostMapping("/register")
    public String registerNewUser(@RequestBody UserRequest request) {
        return userService.register(request);
    }
    
}
