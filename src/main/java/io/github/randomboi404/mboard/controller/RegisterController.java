package io.github.randomboi404.mboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
    
    @GetMapping("/register")
    public String login() {
        return "redirect:/register.html"; 
    }
    
}
