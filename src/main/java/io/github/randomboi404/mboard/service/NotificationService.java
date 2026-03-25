package io.github.randomboi404.mboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;
import org.springframework.scheduling.annotation.Async;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {
    
    private final Map<String, Map<String, SseEmitter>> conversationEmitterMap = new ConcurrentHashMap<>();
    private static final long EMITTER_TIMEOUT = 300_000L;
    
    public SseEmitter getNewEmitter(String conversationId, String sessionId) {
        Map<String, SseEmitter> sessionToEmitterMap = conversationEmitterMap.computeIfAbsent(
                conversationId, 
                k -> new ConcurrentHashMap<>()
        );
        
        SseEmitter existingEmitter = sessionToEmitterMap.get(sessionId);
        if (existingEmitter != null) {
            existingEmitter.complete();
        }
        
        SseEmitter newEmitter = new SseEmitter(EMITTER_TIMEOUT);
        sessionToEmitterMap.put(sessionId, newEmitter);

        newEmitter.onCompletion(() -> sessionToEmitterMap.remove(sessionId, newEmitter));
        newEmitter.onTimeout(() -> sessionToEmitterMap.remove(sessionId, newEmitter));
        newEmitter.onError((e) -> sessionToEmitterMap.remove(sessionId, newEmitter));

        return newEmitter;
    }
    
    public int getActiveEmittersCount(String conversationId) {
        Map<String, SseEmitter> sessionToEmitterMap = conversationEmitterMap.get(conversationId);
        return (sessionToEmitterMap != null) ? sessionToEmitterMap.size() : 0;
    }
    
    @Async
    public void broadcast(String conversationId, SseEventBuilder builder) {
        Map<String, SseEmitter> sessionToEmitterMap = conversationEmitterMap.get(conversationId);
        
        if (sessionToEmitterMap != null) {
            sessionToEmitterMap.forEach((sessionId, emitter) -> {
                try {
                    emitter.send(builder);
                } catch (IOException e) {
                    emitter.completeWithError(e);
                    sessionToEmitterMap.remove(sessionId, emitter);
                }
            });
        }
    }
    
}
