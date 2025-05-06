package com.example.chatservice.domain.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class StompDisconnectEventListener {

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
//        System.out.println("🔌 연결 끊김: " + sessionId);
        log.info("hello : {}",sessionId );

        // 필요 시 사용자 ID 추적 (Connect 시 저장해놔야 함)
    }

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        log.info("hello connected: {}", StompHeaderAccessor.wrap(event.getMessage()).getSessionId());
    }
}
