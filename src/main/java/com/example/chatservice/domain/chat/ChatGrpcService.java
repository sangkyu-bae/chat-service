package com.example.chatservice.domain.chat;

import com.example.chat.ChatMsg;
import com.example.chat.ChatResponse;
import com.example.chat.ChatServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ChatGrpcService extends ChatServiceGrpc.ChatServiceImplBase {
    @Override
    public void sendMessage(ChatMsg request, StreamObserver<ChatResponse> responseObserver) {
        try {
            // 메시지 처리 로직
            ChatResponse response = ChatResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Message received successfully")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void streamMessages(ChatMsg request, StreamObserver<ChatMsg> responseObserver) {
        try {
            // 스트리밍 메시지 처리 로직
            for (int i = 0; i < 10; i++) {
                ChatMsg message = ChatMsg.newBuilder()
                        .setRoomId(request.getRoomId())
                        .setUserId("server")
                        .setContent("Stream message " + i)
                        .setTimestamp(System.currentTimeMillis())
                        .build();

                responseObserver.onNext(message);
                Thread.sleep(1000); // 1초 간격으로 메시지 전송
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
