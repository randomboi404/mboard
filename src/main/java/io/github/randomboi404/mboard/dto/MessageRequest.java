package io.github.randomboi404.mboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MessageRequest(
    @NotBlank(message = "Message cannot be blank")
    @Size(min = 1, max = 3000, message = "Message must be between 1 and 3000 characters")
    String message
) {
    
    public MessageRequest {
        message = (message == null) ? null : message.trim();
    }
    
}
