package io.github.randomboi404.mboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 14, message = "Username must be between 3 and 14 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must be alphanumeric or underscores")
    String username,

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 24, message = "Password must be between 6 and 24 characters")
    String password
) {
    
    public UserRegisterRequest {
        username = (username == null) ? null : username.trim();
    }
    
}
