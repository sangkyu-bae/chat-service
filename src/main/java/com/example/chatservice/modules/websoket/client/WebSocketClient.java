package com.example.chatservice.modules.websoket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.nio.charset.StandardCharsets;


@Slf4j
@Getter
public abstract class WebSocketClient {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    private final String wsUrl;
    private WebSocketSession session;
    public WebSocketClient(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public void connect() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        
        // 메시지 크기 제한 설정
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(10 * 1024 * 1024); // 10MB
        container.setDefaultMaxBinaryMessageBufferSize(10 * 1024 * 1024); // 10MB
        
        client.execute((WebSocketHandler) new SocketHandler(), wsUrl);
    }

    public void disconnect() {
        if (session != null && session.isOpen()) {
            try {
                session.close();
                log.info("WebSocket connection closed.");
            } catch (Exception e) {
                log.error("Error while closing WebSocket session", e);
            }
        }
    }


    protected abstract String getSubscriptionMessage() throws JsonProcessingException; // 거래소별 요청 메시지
    protected abstract void handleIncomingMessage(JsonNode jsonNode); // 거래소별 메시지 처리
    protected abstract void handleRealtimeData(String payload); // 실시간 데이터 처리

    protected abstract void handleCloseSocket(WebSocketClient client);

    private class SocketHandler implements WebSocketHandler {
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            log.info("Connected to WebSocket: {}", wsUrl);
            WebSocketClient.this.session = session;
            try {
                String requestMessage = getSubscriptionMessage();
                session.sendMessage(new TextMessage(requestMessage));
                log.info("Sent subscription message: {}", requestMessage);
            } catch (JsonProcessingException e) {
                log.error("Failed to create subscription message", e);
            }
        }


        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            String payload;
            int messageSize = 0;
            MDC.put("threadId", String.valueOf(Thread.currentThread().getId()));
            // 이진 메시지인 경우 처리
            if (message instanceof BinaryMessage) {
                BinaryMessage binaryMessage = (BinaryMessage) message;
                messageSize = binaryMessage.getPayloadLength();
                payload = new String(binaryMessage.getPayload().array(), StandardCharsets.UTF_8);
                log.info("Binary message size: {} bytes", messageSize);
            } else if (message instanceof TextMessage) {
                // 텍스트 메시지인 경우 처리
                TextMessage textMessage = (TextMessage) message;
                payload = textMessage.getPayload();
                messageSize = payload.getBytes(StandardCharsets.UTF_8).length;
//                log.info("Text message size: {} bytes", messageSize);
            } else {
                log.warn("Received unsupported message type: {}", message.getClass().getName());
                return;
            }

            // 메시지 크기가 제한을 초과하는지 확인
            if (messageSize > 10 * 1024 * 1024) {
                log.warn("Message size ({} bytes) exceeds the limit of 10MB", messageSize);
            }
            // 실시간 데이터 처리
           handleRealtimeData(payload);

            // JSON 처리
            try {
                JsonNode jsonNode = objectMapper.readTree(payload);
                handleIncomingMessage(jsonNode);
            } catch (Exception e) {
                log.error("error");
            }
        }


        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            log.error("WebSocket error: {}", exception.getMessage());
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            handleCloseSocket(WebSocketClient.this);
            log.info("WebSocket closed: {}", closeStatus);
        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }
    }
}

