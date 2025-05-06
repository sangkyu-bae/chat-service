package com.example.chatservice.domain.chat;

import com.example.chatservice.modules.websoket.server.SendMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService extends SendMsg {

    private final ChatRepository chatRepository;

    public void joinChat(String chatRoom,String userId){
        if(chatRepository.existChatRoom(userId,chatRoom)){
            return;
        }

        chatRepository.addChatRoom(userId,chatRoom);
    }

    public void sendMsg(ChatMessage chatMessage){
        joinChat("1",chatMessage.getSendUserId());
    }

    public List<String> findByMyChat(String userId){
        return chatRepository.findByChatRoom(userId);
    }
}
