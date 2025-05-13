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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestWebSocket {

    private final JsonConverter jsonConverter;
    private final ChatService chatService;
    @MessageMapping("/chat/message")
//    @SendTo("/sub/chat/room/1")
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

    @Operation(summary = "채팅전송 테스트", description = "전송 테스트 버추얼 ")
    @GetMapping("/test/soket")
    public String test(){
        long start = System.currentTimeMillis(); // 시작 시간 찍기

        log.info(" start");
        List<Thread> threads = IntStream.range(0, 1_000_0)
                .mapToObj(i -> Thread.ofVirtual().unstarted(()->{
                    ChatMessage chatMessage = new ChatMessage("user" + i , "test " + i, LocalDateTime.now());
                    chatService.sendMsg(chatMessage);
                }))
                .collect(Collectors.toList());

        threads.forEach(Thread::start);


        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis(); // 끝나는 시간 찍기

        log.info("걸린 시간(ms): " + (end - start));
        log.info("걸린 시간(s): " + (end - start) / 1000.0);



        return "ok";
    }

    @Operation(summary = "채팅전송 테스트2", description = "전송 테스트 그냥2")
    @GetMapping("/test/soket2")
    public String test2(){
        long start = System.currentTimeMillis(); // 시작 시간 찍기

        log.info(" start");
        List<Thread> threads = IntStream.range(0, 1_000_0)
                .mapToObj(i -> new Thread(()->{
                    ChatMessage chatMessage = new ChatMessage("user" + i , "test " + i, LocalDateTime.now());
                    chatService.sendMsg(chatMessage);
                }))
                .collect(Collectors.toList());

        threads.forEach(Thread::start);


        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis(); // 끝나는 시간 찍기

        log.info("걸린 시간(ms): " + (end - start));
        log.info("걸린 시간(s): " + (end - start) / 1000.0);



        return "ok";
    }

}
