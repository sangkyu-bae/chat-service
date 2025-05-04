package com.example.chatservice.modules.websoket.server;

public interface SubService<T> {

    void sendMessage(T sendObj);
}
