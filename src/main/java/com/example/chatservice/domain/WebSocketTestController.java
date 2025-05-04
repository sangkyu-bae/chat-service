//package com.example.chatservice.domain;
//
//import com.example.chatservice.modules.websoket.server.StockSendMsg;
//import com.example.chatservice.modules.websoket.server.StockSendMsgService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class WebSocketTestController {
//
//    private final StockSendMsgService stockSendMsgService;
//
//    @PostMapping("/test/send")
//    public String sendTestMessage() {
//        StockSendMsg message = StockSendMsg.createGenerate("테스트 메시지입니다.");
//        stockSendMsgService.sendMessage(message);
//        return "메시지 전송 완료";
//    }
//}