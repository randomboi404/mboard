package io.github.randomboi404.mboard.controller;

import io.github.randomboi404.mboard.dto.UserRegisterRequest;
import io.github.randomboi404.mboard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public String registerNewUser(@Valid @RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }
    
}
