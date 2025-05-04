package com.example.chatservice.modules.websoket.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketManager {

    private final Map<String,Map<String,WebSocketClient>> sockets = new ConcurrentHashMap<>();

    public WebSocketClient findByStockName(String name,String type){
        log.info("socket Find");

        if(!sockets.containsKey(name)){
            return null;
        }

        return sockets.get(type).get(name);
    }

    public void deleteByStockName(String name,String type){
        if(!sockets.containsKey(name)){
            return;
        }

        WebSocketClient webSocketClient =  sockets.get(type).get(name);
        webSocketClient.disconnect();
        sockets.remove(name);
    }

    public WebSocketClient save(String name,String type,WebSocketClient webSocketClient){
        sockets.computeIfAbsent(type, k -> new ConcurrentHashMap<>());
        sockets.get(type).put(name, webSocketClient);
        return webSocketClient;
    }

    public String deleteByWebSocket(WebSocketClient client){
        String deleteKey = null;
        String deleteMapKey = null;



        for(String key : sockets.keySet()){
            Map<String, WebSocketClient> types = sockets.get(key);
            for (Map.Entry<String, WebSocketClient> entry : types.entrySet()) {
                if (entry.getValue() == client) {
                    deleteKey = entry.getKey();
                    deleteMapKey = key;
                    break;
                }
            }
        }


        if (deleteKey != null) {
            sockets.get(deleteMapKey).remove(deleteKey);
        }

        log.info("주식 마켓 : {}의  주식코드 : {} 소켓연결을 종료합니다.: ",deleteMapKey ,deleteKey);



        return deleteKey;
    }

}
