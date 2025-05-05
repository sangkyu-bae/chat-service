package com.example.chatservice.domain.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;

    public void sendMessage(){

    }

    public void joinChat(String chatRoom,String userId){
        chatRepository.addChatRoom(userId,chatRoom);
    }

}
