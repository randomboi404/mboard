package io.github.randomboi404.mboard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String msgId;
    
    private String username;
    private String message;
    private String dateTime;

    public Message(String username, String message, String dateTime) {
        this.username = username;
        this.message = message;
        this.dateTime = dateTime;
    }
    
}
