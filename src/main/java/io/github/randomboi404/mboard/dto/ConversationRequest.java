package io.github.randomboi404.mboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record ConversationRequest(
    @NotBlank
    @Size(min = 1, max = 20, message = "Conversation title must be between 1 and 20 characters")
    String title,

    @NotEmpty
    Set<String> participantIds
) {
    
    public ConversationRequest {
        title = (title == null) ? null : title.trim();
    }
    
}
