package io.github.randomboi404.mboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {
    
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private static final long EMITTER_TIMEOUT = 300_000L;
    
    public SseEmitter getNewEmitter(String sessionId) {
        if (emitters.containsKey(sessionId)) {
            SseEmitter old = emitters.get(sessionId);
            if (old != null) {
                old.complete();
            }
        }
        
        SseEmitter emitter = new SseEmitter(EMITTER_TIMEOUT);
        emitters.put(sessionId, emitter);

        emitter.onCompletion(() -> emitters.remove(sessionId, emitter));
        emitter.onTimeout(() -> emitters.remove(sessionId, emitter));
        emitter.onError((e) -> emitters.remove(sessionId, emitter));

        return emitter;
    }
    
    public int getEmittersCount() {
        return emitters.size();
    }
    
    public void broadcast(SseEventBuilder builder) {
        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(builder);
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(id, emitter);
            }
        });
    }
    
}
