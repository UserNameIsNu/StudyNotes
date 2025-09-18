package edu.ljf.ws.server;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ServerEndpoint("/connect")
public class WebSocketServer {
    private final static Map<String, Session> userMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + "连接");
        userMap.put(session.getId(), session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println(session.getId() + "发送：" + message);
        sendAll(message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(session.getId() + "断开");
        userMap.remove(session.getId());
    }

    public void sendAll(String message) throws IOException {
        Set<String> keys = userMap.keySet();
        for (String key : keys) {
            Session s = userMap.get(key);
            s.getBasicRemote().sendText(message);
        }
    }
}
