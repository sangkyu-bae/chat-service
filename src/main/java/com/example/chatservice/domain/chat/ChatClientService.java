package com.example.chatservice.domain.chat;

import com.example.chat.ChatMsg;
import com.example.chat.ChatResponse;
import com.example.chat.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatClientService {
    private final ChatServiceGrpc.ChatServiceBlockingStub blockingStub;
    private final ChatServiceGrpc.ChatServiceStub asyncStub;

    @Autowired
    public ChatClientService(ChatServiceGrpc.ChatServiceBlockingStub blockingStub,
                             ChatServiceGrpc.ChatServiceStub asyncStub) {
        this.blockingStub = blockingStub;
        this.asyncStub = asyncStub;
    }

    // 일반 메시지 전송
    public ChatResponse sendMessage(String roomId, String userId, String content) {
        ChatMsg message = ChatMsg.newBuilder()
                .setRoomId(roomId)
                .setUserId(userId)
                .setContent(content)
                .setTimestamp(System.currentTimeMillis())
                .build();

        return blockingStub.sendMessage(message);
    }

    // 스트리밍 메시지 수신
    public void streamMessages(String roomId, String userId) {
        ChatMsg message = ChatMsg.newBuilder()
                .setRoomId(roomId)
                .setUserId(userId)
                .setTimestamp(System.currentTimeMillis())
                .build();

        asyncStub.streamMessages(message, new StreamObserver<ChatMsg>() {
            @Override
            public void onNext(ChatMsg message) {
                System.out.println("Received message: " + message.getContent());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in streaming: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Streaming completed");
            }
        });
    }
}
