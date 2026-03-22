package io.github.randomboi404.mboard.model;

import lombok.Data;

@Data
public class Message {
    
    private String message;
    private String username;
    private String idHash;
    private String dateTime;
    
}
