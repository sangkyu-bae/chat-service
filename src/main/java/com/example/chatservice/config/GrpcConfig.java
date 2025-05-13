package com.example.chatservice.config;

import com.example.chat.ChatServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Value("${grpc.server.address:localhost:9090}")
    private String grpcServerAddress;

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forTarget(grpcServerAddress)
                .usePlaintext()
                .build();
    }

    @Bean
    public ChatServiceGrpc.ChatServiceBlockingStub blockingStub(ManagedChannel channel) {
        return ChatServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public ChatServiceGrpc.ChatServiceStub asyncStub(ManagedChannel channel) {
        return ChatServiceGrpc.newStub(channel);
    }
} 