package com.example.chatservice.domain;

import com.example.chatservice.common.JsonConverter;
import com.example.chatservice.domain.chat.ChatMessage;
import com.example.chatservice.domain.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestWebSocket {

    private final JsonConverter jsonConverter;
    private final ChatService chatService;
    @MessageMapping("/chat/message")
    @SendTo("/sub/chat/room/1")
    public String handleMessage(ChatMessage message) {
        message.setSendTime(LocalDateTime.now());
        log.info(jsonConverter.toJson(message));
        chatService.sendMsg(message);

        return jsonConverter.toJson(message);
    }

    @Operation(summary = "채팅방 확인 테스트", description = "채팅방 확인")
    @GetMapping("/test/check")
    public List<String> joinCheck(){
        return chatService.findByMyChat("user");
    }

}
