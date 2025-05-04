package com.example.chatservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class TestWebSocket {
    @MessageMapping("/chat/message")
    @SendTo("/sub/chat/room/1")
    public String handleMessage(String message) {
        log.info("?????");
        log.info(message);
        return message;
    }
}
