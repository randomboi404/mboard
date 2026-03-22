package io.github.randomboi404.mboard.service;

import io.github.randomboi404.mboard.model.Message;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class MessageService {
    
    public List<Message> getMessages() {
        return new ArrayList<>();
    }
    
}
