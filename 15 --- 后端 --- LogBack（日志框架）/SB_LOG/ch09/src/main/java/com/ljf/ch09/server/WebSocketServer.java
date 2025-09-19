package com.ljf.ch09.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljf.ch09.entity.Message;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@ServerEndpoint(value = "/connect", configurator = HandshakeHandler.class)
public class WebSocketServer {
    // 用户会话管理队列
    // 先整个空Map
    private final static Map<String, Session> userMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println(session.getId() + "连接");
        // 每有一个新用户连接，就把这个用户会话塞进Map
        userMap.put(session.getId(), session);
        // 发送在线人数
        sendAll(new Message(userMap.size()));
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println(session.getId() + "发送：" + message);
        // 群发方法
        Message msg = new Message((String) session.getUserProperties().get("uName"), message, new Date());
        sendAll(msg);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println(session.getId() + "断开");
        // 每有一个用户断开连接，就把这个用户从Map中删掉
        userMap.remove(session.getId());
        // 发送在线人数
        sendAll(new Message(userMap.size()));
    }

    public void sendAll(Message message) throws IOException {
        // 以key遍历，这样就可以给所有在线的用户转发这一条信息
        Set<String> keys = userMap.keySet();
        for (String key : keys) {
            // 从用户列表中，根据key取出一个对应会话
            Session s = userMap.get(key);
            // 给这个会话发信息
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(message);
            s.getBasicRemote().sendText(json);
        }
    }
}