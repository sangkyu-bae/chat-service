package com.example.chatservice.domain.chat;

import com.example.chatservice.common.JsonConverter;
import com.example.chatservice.modules.redis.RedisRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ChatRepository extends RedisRepository {

    private JsonConverter jsonConverter;
    public ChatRepository(RedisTemplate<String, Object> redisTemplate,
                          JsonConverter jsonConverter){
        super(redisTemplate);
        this.jsonConverter = jsonConverter;
    }

    public List<ChatMessage> findByChatRoomId(String chatRoomId){
        try{
            return findWithListTypeByAll(chatRoomId).stream()
                    .map(object -> jsonConverter.toObject(object, ChatMessage.class))
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public List<String> findByChatRoom(String userId){
        try{
            Set<Object> rawSet = findWithSetTypeByAll("user:chatrooms:" + userId);
            if (rawSet == null) return List.of();
            return rawSet.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public void addChatRoom(String userId,String chatRoom){
        setTypeSave("user:chatrooms:" + userId,chatRoom);
    }

    public void saveChat(ChatMessage chatMessage,String chatRoom){
        listTypeSave(chatRoom, jsonConverter.toJson(chatMessage));
    }

}
